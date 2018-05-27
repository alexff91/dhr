package com.dhr.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "questions-responds")
public class QuestionRespond {
    @Id
    private Long questionRespondId;
    private String respondId;
    private Long questionId;
    private Boolean answered;
    private String videoPath;
    private Long posterPath;
    private Long duration;
    private Date respondTime;
}
