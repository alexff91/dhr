package com.dhr.controllers.statistic;

import com.dhr.model.statistics.Statistic;
import com.dhr.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@CrossOrigin(origins = "*")
//@RequestMapping("/api/v1/statistic")
public class StatisticRestController {
//    @Autowired
//    StatisticService statisticService;
//
//    @PostMapping
//    public ResponseEntity<Statistic> saveStat(@RequestBody Statistic stat) {
//        return new ResponseEntity<>(statisticService.save(stat), HttpStatus.CREATED);
//    }
//
//    @GetMapping
//    public ResponseEntity<Iterable<Statistic>> getStats() {
//        return new ResponseEntity<>(statisticService.getAll(), HttpStatus.OK);
//    }
}