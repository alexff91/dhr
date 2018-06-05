package com.dhr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    @Column(name = "id", nullable = false)
    private String id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(updatable = false, insertable = false)
    private Vacancy vacancy;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @JsonIgnore
    @OneToMany(targetEntity = QuestionRespond.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "respond_id")
    private List<QuestionRespond> respondQuestions = new LinkedList<>();
}
