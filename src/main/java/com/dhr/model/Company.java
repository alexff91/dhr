package com.dhr.model;

import com.dhr.utils.Constants;
import com.dhr.view.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
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
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(schema = Constants.VI_SCHEMA)
public class Company implements Serializable {
    @JsonView({View.CompanyFull.class, View.CompanyLight.class})
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @JsonView({View.CompanyFull.class, View.CompanyLight.class})
    @Column(length = 4000)
    private String name;

    @JsonView(View.CompanyFull.class)
    @Column(columnDefinition = "TEXT")
    private String logo;

    @JsonView({View.CompanyFull.class})
    @Column(length = 4000)
    private String description;

    @JsonView({View.CompanyFull.class})
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subscription_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Subscription subscription;

    @JsonIgnore
    @JoinTable(schema = Constants.VI_SCHEMA)
    @OneToMany(targetEntity = Vacancy.class, fetch = FetchType.LAZY)
    private List<Vacancy> vacancies = new LinkedList<>();

    @JsonIgnore
    @JoinTable(schema = Constants.VI_SCHEMA, name = "company_skills")
    @OneToMany(targetEntity = Skill.class, fetch = FetchType.LAZY)
    private List<Skill> skills = new LinkedList<>();

    @JsonIgnore
    @JoinTable(schema = Constants.VI_SCHEMA)
    @OneToMany(targetEntity = User.class, fetch = FetchType.LAZY)
    private List<User> users = new LinkedList<>();
}
