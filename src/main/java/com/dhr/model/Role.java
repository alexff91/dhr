package com.dhr.model;

import com.dhr.model.enums.RoleName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "dhr")
public class Role implements Serializable {
    private static final String SEQUENCE_NAME = "role_id_seq";
    @Id
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = "dhr." + SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(updatable = false, insertable = false)
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private RoleName name;
}
