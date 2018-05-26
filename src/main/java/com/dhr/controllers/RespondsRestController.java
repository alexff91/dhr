package com.dhr.controllers;

import com.dhr.model.Respond;
import com.dhr.model.Vacancy;
import com.dhr.services.RespondServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/vacancies/{vacancyId}/responds")
public class RespondsRestController {
    @Autowired
    RespondServiceImpl respondService;

    @GetMapping
    public List<Respond> getRespondsByVacancy(@PathParam("vacancyId") Long vacancyId) {
        return respondService.getAllByVacancyId(vacancyId);
    }
    @PostMapping
    public ResponseEntity createRespond(@RequestBody Respond respond){
        respondService.save(respond);
        return new ResponseEntity(HttpStatus.CREATED);
    }


    @GetMapping("/{respondId}")
    public List<Respond> getRespondById(@PathParam("vacancyId") Long vacancyId,
                                        @PathParam("respondId") Long respondId) {
        return respondService.getByVacancyIdAndRespondId(vacancyId, respondId);
    }
}
