package com.dhr.model;

import com.dhr.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@ToString
@Entity
@Table(schema = "dhr")
public class QuestionAnswerFeedback {
    private static final String SEQUENCE_NAME = "respond_fbk_seq";

    @Id
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = "dhr." + SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private String comment;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "skill")
    @Column(name = "grade")
    @CollectionTable(name = "skills_feedback", schema = Constants.VI_SCHEMA, joinColumns = @JoinColumn(name = "question_answer_feedback_id"))
    private Map<String, Integer> skillsFeedback;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "respond_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private QuestionAnswer questionAnswer;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
