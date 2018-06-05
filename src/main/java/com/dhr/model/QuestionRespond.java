package com.dhr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "question_respond", schema = "vihr")
public class QuestionRespond implements Serializable {
    private static final String SEQUENCE_NAME = "question_respond_id_seq";
    @Id
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = "vihr." + SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(updatable = false, insertable = false)
    private Respond respond;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(updatable = false, insertable = false)
    private Question question;

    private Boolean answered;

    @Column(name = "video_path")
    private String videoPath;

    @Column(name = "poster_path")
    private Long posterPath;

    private Long duration;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "respond_time")
    private Date respondTime;
}
