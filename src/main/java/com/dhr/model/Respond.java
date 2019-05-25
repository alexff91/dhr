package com.dhr.model;

import com.dhr.model.enums.RespondStatus;
import com.dhr.model.enums.ReviewStatus;
import com.dhr.utils.Constants;
import com.dhr.view.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"answers", "startDate"})
@Entity
@Table(schema = "dhr")
public class Respond {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id", nullable = false)
    @JsonIgnore
    private Vacancy vacancy;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "chat_bot")
    private Boolean chatBot = false;

    private String email;

    private String phone;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private Boolean deleted = false;

    @JsonView(View.Detail.class)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private RespondStatus status = RespondStatus.INCOMPLETE;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @JsonView(View.Detail.class)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private ReviewStatus reviewStatus = ReviewStatus.NOT_WATCHED;

    @JsonIgnore
    @OneToMany(targetEntity = QuestionAnswer.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "respond_id")
    @JoinTable(schema = Constants.VI_SCHEMA)
    private List<QuestionAnswer> answers = new LinkedList<>();

    @JsonIgnore
    @JoinTable(schema = Constants.VI_SCHEMA)
    @OneToMany(targetEntity = RespondQuestion.class, fetch = FetchType.LAZY)
    private List<RespondQuestion> respondQuestions = new LinkedList<>();

    @JsonIgnore
    @OneToMany(targetEntity = RespondFeedback.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "respond_id")
    @JoinTable(schema = Constants.VI_SCHEMA)
    private List<RespondFeedback> reviewResponds = new LinkedList<>();
}
