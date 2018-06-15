package com.dhr.model;

import com.dhr.view.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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

    @JsonIgnore
    @OneToMany(targetEntity = Question.class, fetch = FetchType.EAGER)
    private Set<Question> questions = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(targetEntity = QuestionRespond.class, fetch = FetchType.EAGER)
    private Set<QuestionRespond> questionResponds = new LinkedHashSet<>();

    @JsonView(View.Detail.class)
    private Long viewCount;

    @JsonView(View.Detail.class)
    private Long respondCount;

    @JsonView(View.Detail.class)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private VacancyStatus status = VacancyStatus.CREATED;
}
