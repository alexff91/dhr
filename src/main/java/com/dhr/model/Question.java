package com.dhr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = {"vacancy", "skills"})
@Entity
@Table(schema = "dhr")
public class Question implements Serializable {
    private static final String SEQUENCE_NAME = "question_id_seq";

    @Id
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = "dhr." + SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vacancy_id", nullable = false)
    @JsonIgnore
    private Vacancy vacancy;

    @Column(length = 4000)
    private String question;

    @Column(columnDefinition = "TEXT")
    private String videoPath;

    @Column(name = "duration_to_read")
    private Long durationToRead;

    @Column(name = "duration_max")
    private Long durationMax;

    @Column(name = "order_number")
    private Long orderNumber;

    @Column(name = "is_compulsory")
    private Boolean isCompulsory;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Set<QuestionSkill> skills = new LinkedHashSet<>();
}
