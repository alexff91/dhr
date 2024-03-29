package com.dhr.controllers;

import com.dhr.model.Respond;
import com.dhr.model.Vacancy;
import com.dhr.services.RespondServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public List<Respond> getRespondsByVacancy(@PathVariable Long vacancyId) {
        return respondService.getAllByVacancyId(vacancyId);
    }
    @PostMapping
    public ResponseEntity<Respond> createRespond(@RequestBody Respond respond, @PathVariable Long vacancyId){
        respond.setVacancyId(vacancyId);
        return new ResponseEntity<>(respondService.save(respond), HttpStatus.CREATED);
    }

    @GetMapping("/{respondId}")
    public List<Respond> getRespondById(@PathVariable Long vacancyId,
                                        @PathVariable String respondId) {
        return respondService.getByVacancyIdAndRespondId(vacancyId, respondId);
    }
}
