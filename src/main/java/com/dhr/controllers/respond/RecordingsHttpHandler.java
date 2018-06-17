package com.dhr.controllers.respond;

import com.dhr.config.PropertiesConfig;
import com.dhr.model.QuestionAnswer;
import com.dhr.model.Respond;
import com.dhr.model.enums.RespondStatus;
import com.dhr.services.QuestionRespondService;
import com.dhr.services.QuestionService;
import com.dhr.services.RespondService;
import com.dhr.utils.MultipartFileSender;
import com.dhr.utils.StorageService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/responds/{respondId}/questions/")
public class RecordingsHttpHandler {

    private static final Logger log = LoggerFactory.getLogger(RecordingsHttpHandler.class);

    @Autowired
    PropertiesConfig config;

    @Autowired
    QuestionRespondService questionRespondService;

    @Autowired
    QuestionService questionService;

    @Autowired
    RespondService respondService;

    @RequestMapping(value = "/{questionId}/{filename:.+}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<HttpStatus> handleGetRecording(@PathVariable String respondId,
                                                         @PathVariable Long questionId,
                                                         HttpServletRequest request,
                                                         HttpServletResponse response) throws Exception {

        Long companyId = respondService.get(respondId).get().getVacancy().getCompany().getId();
        File file = new File(config.getRecordingsPath() + "/company/"+ companyId + "/responds/" + respondId + "/questions/", questionId + ".webm");
        if (file.isFile()) {
            MultipartFileSender.fromPath(file.toPath()).with(request).with(response).serveResource();
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<String>> handleGetRecordings(HttpServletRequest request,
                                                            @PathVariable String respondId,
                                                            HttpServletResponse response)
            throws Exception {
        List<String> results = new ArrayList<>();

        File[] filesUser = new File(config.getRecordingsPath() + getRecordingPath(respondId)).listFiles();
        assert filesUser != null;
        for (File file : filesUser) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    private String getRecordingPath(@PathVariable String respondId) {
        Long companyId = respondService.get(respondId).get().getVacancy().getCompany().getId();
        return  "/company/"+ companyId + "/responds/" + respondId + "/questions/";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{questionId}")
    @Transactional
    public ResponseEntity handlePostRecording(HttpServletRequest request,
                                              @PathVariable String respondId,
                                              @PathVariable Long questionId,
                                              @RequestParam("file") MultipartFile file) throws IOException {

        if (saveVideoToDirectory(respondId, questionId, file))
            return new ResponseEntity<>("File is empty", HttpStatus.OK);

        QuestionAnswer questionAnswer = QuestionAnswer.builder()
                .question(questionService.get(questionId).get())
                .respond(respondService.get(respondId).get())
                .videoPath("https://" + config.getBackendHost() +
                        ":" + config.getServerPort() +
                        "/api/v1/responds/" + respondId +
                        "/questions/" + questionId + "/" + questionId + ".webm")
                .answered(true)
                .respondTime(new Date())
                .build();

        Respond respond = respondService.get(respondId).get();
        List<QuestionAnswer> respondQuestions = respond.getAnswers();
        Optional<QuestionAnswer> answeredRespond = respond.getAnswers().stream()
                .filter(questionRespondAnswered -> Objects.equals(questionRespondAnswered.getQuestion().getId(), questionAnswer.getId())).findAny();
        if (answeredRespond.isPresent()) {
            respond.getAnswers().set(respond.getAnswers().indexOf(answeredRespond.get()), questionAnswer);
        } else if (respondQuestions != null) {
            respondQuestions.add(questionAnswer);
            respond.setAnswers(respondQuestions);
        }
        questionRespondService.save(questionAnswer);
        if (respond.getVacancy().getQuestions().size() == respond.getAnswers().size()) {
            respond.setStatus(RespondStatus.COMPLETE);
            respondService.save(respond, respond.getVacancy().getId());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean saveVideoToDirectory(@PathVariable String respondId, @PathVariable Long questionId, @RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            log.error("File is empty");
            return true;
        }

        String folder = getRecordingPath(respondId);

        Path path = Paths.get(config.getRecordingsPath() + folder);
        boolean mkdirsResult = new File(path.toString()).mkdirs();
        String fName = questionId + ".webm";

        File uploadedFile = new File(path.toFile(), fName);
        InputStream initialStream = file.getInputStream();
        Files.copy(initialStream, uploadedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        IOUtils.closeQuietly(initialStream);
        return false;
    }

}
