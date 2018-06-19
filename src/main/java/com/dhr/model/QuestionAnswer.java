package com.dhr.model;

import com.dhr.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "question_answer", schema = Constants.VI_SCHEMA)
public class QuestionAnswer implements Serializable {
    private static final String SEQUENCE_NAME = "question_respond_id_seq";

    @Id
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = "vihr." + SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private Boolean answered;

    @Column(name = "video_path", length = 4000)
    private String videoPath;

    @Column(name = "poster_path", length = 4000)
    private Long posterPath;

    private Long duration;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "respond_time")
    private Date respondTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "respond_id", nullable = false)
    @JsonIgnore
    private Respond respond;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private RespondQuestion question;

    @JsonIgnore
    @JoinTable(schema = Constants.VI_SCHEMA)
    @OneToMany(targetEntity = QuestionAnswerFeedback.class, fetch = FetchType.LAZY)
    private List<QuestionAnswerFeedback> skillsFeedback = new LinkedList<>();
}
