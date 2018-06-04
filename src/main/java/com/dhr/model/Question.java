package com.dhr.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@Table(schema = "vihr")
public class Question implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JsonBackReference("questions")
    @JoinColumn(updatable = false, insertable = false)
    private Vacancy vacancy;

    private String question;

    @Column(name = "duration_to_read")
    private Long durationToRead;

    @Column(name = "duration_max")
    private Long durationMax;

    @Column(name = "order_number")
    private Long orderNumber;

    @Column(name = "is_compulsory")
    private Boolean isCompulsory;
}
