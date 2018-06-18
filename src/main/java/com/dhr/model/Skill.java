package com.dhr.model;

import com.dhr.model.enums.SkillStatus;
import com.dhr.view.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
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
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@Table(schema = "vihr")
public class Skill implements Serializable {
    private static final String SEQUENCE_NAME = "skill_id_seq";
    @Id
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = "vihr." + SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "id", nullable = false)
    Long id;
    
    String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonView(View.Base.class)
    private Company company;
    
    @Column(name = "skill_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private SkillStatus status = SkillStatus.ACTIVE;
}
