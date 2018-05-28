package com.dhr.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = "respondQuestions")
@Document(collection = "responds")
public class Respond {
    @Id
    private String respondId;
    private Long vacancyId;
    private String name;
    private String lastName;
    private String email;
    private String status;
    private Date startDate;
    @DBRef
    private List<QuestionRespond> respondQuestions = new LinkedList<>();
}
