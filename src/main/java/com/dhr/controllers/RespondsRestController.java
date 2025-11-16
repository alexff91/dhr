package com.dhr.controllers;

import com.dhr.model.Respond;
import com.dhr.services.RespondServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/vacancies/{vacancyId}/responds")
public class RespondsRestController {

    private final RespondServiceImpl respondService;

    public RespondsRestController(RespondServiceImpl respondService) {
        this.respondService = respondService;
    }

    @GetMapping
    public ResponseEntity<List<Respond>> getRespondsByVacancy(@PathVariable Long vacancyId) {
        return ResponseEntity.ok(respondService.getAllByVacancyId(vacancyId));
    }

    @PostMapping
    public ResponseEntity<Respond> createRespond(@RequestBody Respond respond, @PathVariable Long vacancyId) {
        respond.setVacancyId(vacancyId);
        Respond savedRespond = respondService.save(respond);
        return new ResponseEntity<>(savedRespond, HttpStatus.CREATED);
    }

    @GetMapping("/{respondId}")
    public ResponseEntity<List<Respond>> getRespondById(@PathVariable Long vacancyId,
                                                          @PathVariable String respondId) {
        List<Respond> responds = respondService.getByVacancyIdAndRespondId(vacancyId, respondId);
        return ResponseEntity.ok(responds);
    }
}
