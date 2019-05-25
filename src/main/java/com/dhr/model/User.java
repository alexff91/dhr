package com.dhr.model;

import com.dhr.utils.Constants;
import com.dhr.view.View;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(schema = "dhr")
public class User implements Serializable {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String login;

    @JsonIgnore
    private String password;

    private String phone;

    private Boolean deleted = false;

    @JoinTable(schema = Constants.VI_SCHEMA)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("roles")
    private List<Role> roles;

    @JsonView(View.Detail.class)
    @JoinTable(schema = Constants.VI_SCHEMA)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference("respondFeedbacks")
    private List<RespondFeedback> respondFeedbacks;

    @Column(name = "avatar_path")
    private String avatarPath;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn
    private Company company;
}
