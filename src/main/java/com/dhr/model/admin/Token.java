package com.dhr.model.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(schema = "dhr")
public class Token implements Serializable {
    private static final String SEQUENCE_NAME = "token_id_seq";
    @Id
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = "dhr." + SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private String token;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
}
