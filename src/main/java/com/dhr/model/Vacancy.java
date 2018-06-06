package com.dhr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private static final String SEQUENCE_NAME = "vacancy_id_seq";
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @JsonIgnore
    @ManyToOne
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

    @JsonIgnore
    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference("questions")
    private Set<Question> questions = new LinkedHashSet<>();
}
