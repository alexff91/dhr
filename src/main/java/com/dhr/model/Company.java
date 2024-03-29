package com.dhr.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "companies")
public class Company {
    @Id
    private Long companyId;
    private String companyName;
    private String companyLogoPath;
    private String companyDescription;
    private Date creationDate;
    private Subscription subscription;
    @DBRef
    private List<Vacancy> vacancies = new LinkedList<>();
    @DBRef
    private List<User> users = new LinkedList<>();
}
