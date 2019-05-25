package com.dhr.model.statistics;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(schema = "dhr")
public class Statistic {
    private static final String SEQUENCE_NAME = "company_id_seq";
    @Id
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = "dhr." + SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String data;
}
