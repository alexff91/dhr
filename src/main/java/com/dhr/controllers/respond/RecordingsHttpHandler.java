package com.dhr.controllers.respond;

import com.dhr.config.PropertiesConfig;
import com.dhr.model.Question;
import com.dhr.model.QuestionAnswer;
import com.dhr.model.Respond;
import com.dhr.model.RespondQuestion;
import com.dhr.model.RespondSkill;
import com.dhr.model.enums.RespondStatus;
import com.dhr.services.QuestionAnswerService;
import com.dhr.services.QuestionService;
import com.dhr.services.RespondQuestionService;
import com.dhr.services.RespondService;
import com.dhr.services.RespondSkillService;
import com.dhr.utils.MultipartFileSender;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class RecordingsHttpHandler {

    private static final Logger log = LoggerFactory.getLogger(RecordingsHttpHandler.class);
    private static final String WEBM_VIDEO_FORMAT = ".webm";

    @Autowired
    PropertiesConfig config;

    @Autowired
    QuestionAnswerService questionAnswerService;

    @Autowired
    RespondQuestionService respondQuestionService;

    @Autowired
    RespondSkillService respondSkillService;

    @Autowired
    QuestionService questionService;

    @Autowired
    RespondService respondService;

    @GetMapping("/api/v1/responds/{respondId}/answers/{answerId}/{filename:.+}")
    @Transactional
    public ResponseEntity<HttpStatus> serveFile(@PathVariable String respondId,
                                                @PathVariable Long answerId,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {

        String companyId = respondService.get(respondId).get().getVacancy().getCompany().getId();
        File file = new File(config.getRecordingsPath() + "/company/" + companyId + "/responds/" + respondId + "/answers/" + answerId + "/", answerId + ".webm");
        if (file.isFile()) {
            MultipartFileSender.fromPath(file.toPath()).with(request).with(response).serveResource();
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private String getRecordingPath(@PathVariable String respondId, Long answerId) {
        String companyId = respondService.get(respondId).get().getVacancy().getCompany().getId();
        return "/company/" + companyId + "/responds/" + respondId + "/answers/" + answerId + "/";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/responds/{respondId}/questions/{questionId}")
    @Transactional
    public ResponseEntity handlePostRecording(HttpServletRequest request,
                                              @PathVariable String respondId,
                                              @PathVariable Long questionId,
                                              @RequestParam("file") MultipartFile file) throws IOException {

        Question question = questionService.get(questionId).get();
        Respond respond = respondService.get(respondId).get();
        RespondQuestion savedRespondQuestion = copyAndSaveQuestionToRespondQuestion(respondId, question, respond);
        if (saveVideoToDirectory(respondId, savedRespondQuestion.getId(), file))
            return new ResponseEntity<>("File is empty", HttpStatus.OK);

        QuestionAnswer questionAnswer = QuestionAnswer.builder()
                .question(savedRespondQuestion)
                .respond(respond)
                .answered(true)
                .respondTime(new Date())
                .build();

        QuestionAnswer checkedAnswer = checkAnswers(respond, questionAnswer);
        checkedAnswer.setVideoPath("https://" + config.getBackendHost() +
                ":" + config.getServerPort() +
                "/api/v1/responds/" + respondId +
                "/answers/" + savedRespondQuestion.getId() + "/" + savedRespondQuestion.getId() + ".webm");
        questionAnswerService.update(checkedAnswer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private QuestionAnswer checkAnswers(Respond respond, QuestionAnswer questionAnswer) {
        List<QuestionAnswer> questionAnswers = respond.getAnswers();
        Optional<QuestionAnswer> answeredRespond = respond.getAnswers().stream()
                .filter(questionRespondAnswered -> Objects.equals(questionRespondAnswered.getQuestion().getId(), questionAnswer.getId())).findAny();
        if (answeredRespond.isPresent()) {
            respond.getAnswers().set(respond.getAnswers().indexOf(answeredRespond.get()), questionAnswer);
        } else if (questionAnswers != null) {
            questionAnswers.add(questionAnswer);
            respond.setAnswers(questionAnswers);
        }
        QuestionAnswer save = questionAnswerService.save(questionAnswer);
        if (respond.getVacancy().getQuestions().size() == respond.getAnswers().size()) {
            respond.setStatus(RespondStatus.COMPLETE);
            respondService.update(respond);
        }
        return save;
    }

    private RespondQuestion copyAndSaveQuestionToRespondQuestion(String respondId, Question question, Respond respond) {
        RespondQuestion respondQuestion = RespondQuestion.builder()
                .question(question.getQuestion())
                .respond(respond)
                .durationMax(question.getDurationMax())
                .isCompulsory(question.getIsCompulsory())
                .durationToRead(question.getDurationToRead())
                .orderNumber(question.getOrderNumber())
                .build();
        if (question.getSkills() != null) {
            respondQuestion.setRespondSkills(question.getSkills()
                    .stream()
                    .map(skill -> {
                        RespondSkill build = RespondSkill.builder()
                                .name(skill.getName())
                                .build();
                        return respondSkillService.save(build);
                    }).collect(Collectors.toSet()));
        }
        return respondQuestionService.save(respondQuestion, respondId);
    }

    private boolean saveVideoToDirectory(String respondId, Long answerId, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            log.error("File is empty");
            return true;
        }

        String folder = getRecordingPath(respondId, answerId);

        Path path = Paths.get(config.getRecordingsPath() + folder);
        new File(path.toString()).mkdirs();
        String fName = answerId + WEBM_VIDEO_FORMAT;

        File uploadedFile = new File(path.toFile(), fName);
        InputStream initialStream = file.getInputStream();
        Files.copy(initialStream, uploadedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        IOUtils.closeQuietly(initialStream);
        return false;
    }
}
