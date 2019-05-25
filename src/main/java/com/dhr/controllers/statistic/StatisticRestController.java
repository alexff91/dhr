package com.dhr.controllers.statistic;

import com.dhr.model.statistics.Statistic;
import com.dhr.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/secured/statistic")
public class StatisticRestController {
    @Autowired
    StatisticService statisticService;

    @PostMapping
    public ResponseEntity<Statistic> saveStat(@RequestBody Statistic stat) {
        return new ResponseEntity<>(statisticService.save(stat), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<Statistic>> getStats() {
        return new ResponseEntity<>(statisticService.getAll(), HttpStatus.OK);
    }
}