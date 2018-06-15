package com.dhr.controllers.respond;

import com.dhr.model.Respond;
import com.dhr.model.Vacancy;
import com.dhr.services.RespondServiceImpl;
import com.dhr.services.VacancyService;
import com.dhr.view.View;
import com.fasterxml.jackson.annotation.JsonView;
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

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/vacancies/{vacancyId}/responds")
public class RespondsRestController {
    @Autowired
    RespondServiceImpl respondService;

    @Autowired
    VacancyService vacancyService;

    @GetMapping
    public List<Respond> getRespondsByVacancy(@PathVariable String vacancyId) {
        return respondService.getAllByVacancyId(vacancyId);
    }

    @JsonView(View.Detail.class)
    @GetMapping
    public List<Respond> getRespondsByVacancyDetailed(@PathVariable String vacancyId) {
        return respondService.getAllByVacancyId(vacancyId);
    }

    @PostMapping
    public ResponseEntity<Respond> createRespond(@RequestBody Respond respond,
                                                 @PathVariable String vacancyId) {
        Vacancy vacancy = vacancyService.get(vacancyId).get();
        respond.setVacancy(vacancy);
        return new ResponseEntity<>(respondService.save(respond), HttpStatus.CREATED);
    }

    @GetMapping("/{respondId}")
    public List<Respond> getRespondById(@PathVariable String vacancyId,
                                        @PathVariable String respondId) {
        return respondService.getByVacancyIdAndRespondId(vacancyId, respondId);
    }

    @JsonView(View.Detail.class)
    @GetMapping("/{respondId}/detailed")
    public List<Respond> getRespondByIdDetailed(@PathVariable String vacancyId,
                                                @PathVariable String respondId) {
        return respondService.getByVacancyIdAndRespondId(vacancyId, respondId);
    }
}
