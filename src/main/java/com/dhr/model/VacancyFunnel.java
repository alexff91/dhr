package com.dhr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@ToString
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "vihr")
public class VacancyFunnel {
    private static final String SEQUENCE_NAME = "funnel_id_seq";
    @Id
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = "vihr." + SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "id", nullable = false)
    Long id;
    @JsonIgnore
    @OneToOne
    Vacancy vacancy;
    Long pageVisits;
    Long chatbot;
    Long passedFilter;
    Long startedVideoInterview;
    Long endedVideoInterview;
}
