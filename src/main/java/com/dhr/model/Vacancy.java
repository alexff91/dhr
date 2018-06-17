package com.dhr.model;

import com.dhr.model.enums.VacancyStatus;
import com.dhr.utils.Constants;
import com.dhr.view.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@EqualsAndHashCode(exclude = {"creationDate"})
@Table(name = "vacancy", schema = "vihr")
public class Vacancy implements Serializable {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonView(View.Base.class)
    private Company company;

    @JsonIgnore
    @ManyToOne
    private User user;

    @Column(length = 4000)
    private String position;

    @Column(length = 4000)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;

    @JsonView(View.Detail.class)
    @JoinTable(schema = Constants.VI_SCHEMA)
    @OneToMany(targetEntity = Question.class, fetch = FetchType.EAGER)
    private Set<Question> questions = new LinkedHashSet<>();

    @JsonIgnore
    @JoinTable(schema = Constants.VI_SCHEMA)
    @OneToMany(targetEntity = QuestionAnswer.class, fetch = FetchType.LAZY)
    private Set<QuestionAnswer> questionAnswers = new LinkedHashSet<>();

    @JsonView(View.Detail.class)
    private Long viewsCount = 0L;

    @JsonView(View.Detail.class)
    private Long respondsCount = 0L;

    @JsonView(View.Detail.class)
    private Long unansweredRespondsCount = 0L;

    @JsonView(View.Detail.class)
    private Long minReviewCount = 1L;

    @JsonView(View.Detail.class)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private VacancyStatus status = VacancyStatus.IN_WORK;
}
