package com.dhr.controllers;

import com.dhr.config.PropertiesConfig;
import com.dhr.utils.MultipartFileSender;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import java.util.List;

import static com.dhr.utils.FileUtils.findFileName;
import static com.dhr.utils.FileUtils.getFileName;
import static com.google.common.io.Files.getFileExtension;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/vacancy/{vacancyId}/responds/{respondId}/questions/")
public class RecordingsHttpHandler {

    private static final Logger log = LoggerFactory.getLogger(RecordingsHttpHandler.class);

    @Autowired
    PropertiesConfig config;

    @RequestMapping(value = "/{questionId}/{filename:.+}", method = RequestMethod.GET)
    public ResponseEntity<HttpStatus> handleGetRecording(@PathVariable Long vacancyId,
                                                         @PathVariable Long respondId,
                                                         @PathVariable Long questionId,
                                                         HttpServletRequest request,
                                                         HttpServletResponse response) throws Exception {

        File file = new File(config.getRecordingsPath() + "/vacancy/" + vacancyId + "/responds/" + respondId + "/questions/", questionId + ".webm");

        if (file.isFile()) {
            MultipartFileSender.fromPath(file.toPath()).with(request).with(response).serveResource();
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ResponseEntity<List<String>> handleGetRecordings(HttpServletRequest request,
                                                            @PathVariable Long vacancyId,
                                                            @PathVariable Long respondId,
                                                            @PathVariable Long questionId,
                                                            HttpServletResponse response)
            throws Exception {
        List<String> results = new ArrayList<>();

        File[] filesUser = new File(config.getRecordingsPath() + "/vacancy/" + vacancyId + "/responds/" + respondId + "/questions/").listFiles();
        assert filesUser != null;
        for (File file : filesUser) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> handlePostRecording(HttpServletRequest request,
                                                          @PathVariable Long respondId,
                                                          @PathVariable Long vacancyId,
                                                          @PathVariable Long questionId,
                                                          @RequestParam("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            log.error("File is empty");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String folder = "/vacancy/" + vacancyId + "/responds/" + respondId + "/questions/";

        Path path = Paths.get(config.getRecordingsPath() + folder);
        String fName = questionId + ".webm";

        File uploadedFile = new File(path.toFile(), fName);
        InputStream initialStream = file.getInputStream();
        Files.copy(initialStream, uploadedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        IOUtils.closeQuietly(initialStream);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
