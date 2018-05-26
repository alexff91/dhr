package com.dhr.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "questions")
public class Question {
    @Id
    private Long id;
    private Long interviewId;
    private String question;
    private Long durationToRead;
    private Long durationMax;
    private Long orderNumber;
    private Boolean isCompulsory;
}
