package com.dhr.model;

import com.dhr.model.enums.RespondFeedbackStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(schema = "vihr")
public class RespondFeedback {
    private static final String SEQUENCE_NAME = "respond_fbk_seq";

    @Id
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = "vihr." + SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private String comment;

    @Column(name = "respond_feedback_status", nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private RespondFeedbackStatus respondFeedbackStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "respond_id", nullable = false)
    @JsonIgnore
    private Respond respond;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}