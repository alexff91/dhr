package com.dhr.model;

import com.dhr.model.enums.SkillStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@EqualsAndHashCode(exclude = {"id"})
@Table(schema = "dhr")
public class QuestionSkill implements Serializable {
    private static final String SEQUENCE_NAME = "question_skill_id_seq";
    @JsonIgnore
    @Id
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = "dhr." + SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "id", nullable = false)
    Long id;

    String name;

    @JsonIgnore
    private Boolean deleted = false;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Question question;

    @JsonIgnore
    @Column(name = "skill_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private SkillStatus status = SkillStatus.ACTIVE;
}
