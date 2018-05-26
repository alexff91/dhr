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
@Document(collection = "responders")
public class Responder {
    @Id
    private Long responderId;
    @DBRef
    List<Respond> responds;
    private String name;
    private String lastName;
    private String email;
}
