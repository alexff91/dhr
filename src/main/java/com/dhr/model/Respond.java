package com.dhr.model;

import com.dhr.model.enums.RespondStatus;
import com.dhr.model.enums.ReviewStatus;
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

import javax.persistence.CascadeType;
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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"answers", "startDate"})
@Entity
@Table(schema = "vihr")
public class Respond {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "vacancy_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Vacancy vacancy;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String phone;

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
    @OneToMany(targetEntity = RespondFeedback.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "respond_id")
    @JoinTable(schema = Constants.VI_SCHEMA)
    private List<RespondFeedback> reviewResponds = new LinkedList<>();
}
