package com.dhr.model;

import com.dhr.utils.Constants;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(schema = "vihr")
public class Question implements Serializable {
    private static final String SEQUENCE_NAME = "question_id_seq";

    @Id
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = "vihr." + SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "vacancy_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Vacancy vacancy;

    @Column(length = 4000)
    private String question;

    @Column(name = "duration_to_read")
    private Long durationToRead;

    @Column(name = "duration_max")
    private Long durationMax;

    @Column(name = "order_number")
    private Long orderNumber;

    @Column(name = "is_compulsory")
    private Boolean isCompulsory;

    @JoinTable(schema = Constants.VI_SCHEMA)
    @ManyToMany(targetEntity = Skill.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Skill> skills = new LinkedHashSet<>();
}
