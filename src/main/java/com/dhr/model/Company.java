package com.dhr.model;

import com.dhr.utils.Constants;
import com.dhr.view.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Entity
@Table(schema = Constants.VI_SCHEMA)
public class Company implements Serializable {
    private static final String SEQUENCE_NAME = "company_id_seq";

    @JsonView({View.CompanyFull.class, View.CompanyLight.class})
    @Id
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = "vihr." + SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

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
    @JsonManagedReference
    @JoinTable(schema = Constants.VI_SCHEMA)
    @OneToMany(targetEntity = Skill.class, fetch = FetchType.LAZY)
    private List<Skill> skills = new LinkedList<>();

    @JsonIgnore
    @JoinTable(schema = Constants.VI_SCHEMA)
    @OneToMany(targetEntity = User.class, fetch = FetchType.LAZY)
    private List<User> users = new LinkedList<>();
}
