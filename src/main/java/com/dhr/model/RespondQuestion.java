package com.dhr.model;

import com.dhr.utils.Constants;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "dhr")
public class RespondQuestion implements Serializable {
    private static final String SEQUENCE_NAME = "respond_question_id_seq";

    @Id
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = "dhr." + SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "respond_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Respond respond;

    @Column(length = 4000)
    private String question;

    @Column(name = "duration_to_read")
    private Long durationToRead;

    @Column(name = "duration_max")
    private Long durationMax;

    @Column(name = "order_number")
    private Long orderNumber;

    @Column(name = "is_compulsory")
    private Boolean isCompulsory;

    @JsonIgnore
    @JoinTable(schema = Constants.VI_SCHEMA)
    @OneToMany(targetEntity = QuestionAnswer.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference("questionAnswers")
    private Set<QuestionAnswer> questionAnswers = new LinkedHashSet<>();

    @JoinTable(schema = Constants.VI_SCHEMA)
    @ManyToMany(targetEntity = RespondSkill.class, fetch = FetchType.EAGER)
    private Set<RespondSkill> respondSkills = new LinkedHashSet<>();
}
