package com.dhr.model;

import com.dhr.model.enums.SubscriptionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(schema = "dhr")
public class Subscription implements Serializable {
    private static final String SEQUENCE_NAME = "subscription_id_seq";
    @Id
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = "dhr." + SEQUENCE_NAME, allocationSize = 1)

    @Column(name = "id", nullable = false)
    Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "subscription", fetch = FetchType.LAZY)
    private Set<Company> company;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "subscription_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @NonNull
    private SubscriptionType type;
}
