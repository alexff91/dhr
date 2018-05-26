package com.dhr.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "responds")
public class Respond {
    @Id
    private Long respondId;
    private Long vacancyId;
    private Long responderId;
    private String status;
    @DBRef
    private List<QuestionRespond> respondQuestions;
}
