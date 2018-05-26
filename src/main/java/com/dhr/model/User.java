package com.dhr.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String role;
    private String avatarPath;
    private Long companyId;
}
