package com.dhr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@EqualsAndHashCode(exclude = {"vacancy"})
@Table(schema = "dhr")
public class VacancyFunnel {
    private static final String SEQUENCE_NAME = "funnel_id_seq";
    @Id
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = "dhr." + SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "id", nullable = false)
    Long id;
    @JsonIgnore
    @OneToOne
    Vacancy vacancy;
    Long pageVisits = 0L;
    Long chatbot = 0L;
    Long passedFilter = 0L;
    Long startedVideoInterview = 0L;
    Long endedVideoInterview = 0L;
}
