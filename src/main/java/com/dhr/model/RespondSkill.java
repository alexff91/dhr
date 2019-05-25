package com.dhr.model;

import com.dhr.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
public class RespondSkill implements Serializable {
    private static final String SEQUENCE_NAME = "respond_skill_id_seq";
    @Id
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = "dhr." + SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "id", nullable = false)
    Long id;

    String name;

    @JoinTable(schema = Constants.VI_SCHEMA)
    @ManyToMany(targetEntity = RespondQuestion.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<RespondQuestion> questions = new LinkedHashSet<>();
}
