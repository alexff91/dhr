package com.dhr.model;

import com.dhr.model.enums.RespondReviewStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(schema = "dhr")
public class RespondFeedback {
    private static final String SEQUENCE_NAME = "respond_fbk_seq";

    @Id
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = "dhr." + SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private String comment;

    @Column(name = "respond_review_status", nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private RespondReviewStatus respondReviewStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "respond_id", nullable = false)
    @JsonIgnore
    private Respond respond;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}