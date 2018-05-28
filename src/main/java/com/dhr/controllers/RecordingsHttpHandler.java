package com.dhr.controllers;

import com.dhr.config.PropertiesConfig;
import com.dhr.model.QuestionRespond;
import com.dhr.model.Respond;
import com.dhr.services.QuestionRespondService;
import com.dhr.services.RespondService;
import com.dhr.utils.MultipartFileSender;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    RespondService respondService;

    @RequestMapping(value = "/{questionId}/{filename:.+}", method = RequestMethod.GET)
    public ResponseEntity<HttpStatus> handleGetRecording(@PathVariable String respondId,
                                                         @PathVariable Long questionId,
                                                         HttpServletRequest request,
                                                         HttpServletResponse response) throws Exception {

        File file = new File(config.getRecordingsPath() + "/responds/" + respondId + "/questions/", questionId + ".webm");

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
        return "/responds/" + respondId + "/questions/";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{questionId}")
    public ResponseEntity<HttpStatus> handlePostRecording(HttpServletRequest request,
                                                          @PathVariable String respondId,
                                                          @PathVariable Long questionId,
                                                          @RequestParam("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            log.error("File is empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String folder = getRecordingPath(respondId);

        Path path = Paths.get(config.getRecordingsPath() + folder);
        new File(path.toString()).mkdirs();
        String fName = questionId + ".webm";

        File uploadedFile = new File(path.toFile(), fName);
        InputStream initialStream = file.getInputStream();
        Files.copy(initialStream, uploadedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        IOUtils.closeQuietly(initialStream);

        QuestionRespond questionRespond = QuestionRespond.builder()
                .questionId(questionId)
                .questionRespondId((long) questionRespondService.getAll().size())
                .respondId(respondId)
                .videoPath("https://168.63.13.234:8080/api/v1/responds/" + respondId +
                        "/questions/" + questionId + "/" + questionId + ".webm")
                .answered(true)
                .respondTime(new Date())
                .build();

        Respond respond = respondService.get(respondId).get();
        List<QuestionRespond> respondQuestions = respond.getRespondQuestions();
        if (respondQuestions != null) {
            respondQuestions.add(questionRespond);
            respond.setRespondQuestions(respondQuestions);
        }
        respondService.save(respond);
        questionRespondService.save(questionRespond);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
