package com.dhr.controllers;

import com.dhr.model.Respond;
import com.dhr.services.RespondServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/interviews/{interviewId}/responds")
public class RespondsRestController {
    @Autowired
    RespondServiceImpl respondService;

    @GetMapping
    public List<Respond> getRespondsByInterview(@PathParam("interviewId") Long interviewId) {
        return respondService.getAllByInterviewId(interviewId);
    }

    @GetMapping("/{respondId}")
    public List<Respond> getRespondById(@PathParam("interviewId") Long interviewId,
                                        @PathParam("respondId") Long respondId) {
        return respondService.getByInterviewIdAndRespondId(interviewId, respondId);
    }
}
