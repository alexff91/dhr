package com.dhr.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
    @DBRef
    private List<Interview> interviews;
}
