package com.dhr.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "interviews")
public class Interview {
    @Id
    private Long interviewId;
    private Long companyId;
    private Long userId;
    private String position;
    private String description;
    private Date creationDate;
    @DBRef
    private List<Question> questions;
}
