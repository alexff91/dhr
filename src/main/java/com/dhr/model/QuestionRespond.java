package com.dhr.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@ToString
@Document(collection = "questions-responds")
public class QuestionRespond {
    @Id
    private Long questionRespondId;
    private Long respondId;
    private Long questionId;
    private Boolean answered;
    private String comment;
    private String videoPath;
    private Long posterPath;
    private Long duration;
    private Date respondTime;
}
