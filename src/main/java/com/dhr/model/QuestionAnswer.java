package com.dhr.model;

import com.dhr.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
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
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = "dhr." + SEQUENCE_NAME, allocationSize = 1)
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RespondQuestion question;

    @JsonIgnore
    @JoinTable(schema = Constants.VI_SCHEMA)
    @OneToMany(targetEntity = QuestionAnswerFeedback.class, fetch = FetchType.EAGER)
    private List<QuestionAnswerFeedback> skillsFeedback = new LinkedList<>();
}
