package com.dhr.model;

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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"respondQuestions", "startDate"})
@Entity
public class Respond {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Vacancy vacancy;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    private String email;

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
    @OneToMany(targetEntity = QuestionRespond.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "respond_id")
    private List<QuestionRespond> respondQuestions = new LinkedList<>();
}
