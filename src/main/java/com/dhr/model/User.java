package com.dhr.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "users")
public class User {
    @Id
    private Long userId;
    private String name;
    private String lastName;
    private String email;
    private String login;
    private String password;
    private String phone;
    private List<Role> roles;
    private String avatarPath;
    private Long companyId;
}
