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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Table(schema = "dhr")
public class Skill implements Serializable {
    private static final String SEQUENCE_NAME = "skill_id_seq";
    @Id
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = "dhr." + SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(unique = true)
    String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @JsonIgnore
    private Boolean deleted = false;

    @JsonIgnore
    @Column(name = "skill_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private SkillStatus status = SkillStatus.ACTIVE;
}
