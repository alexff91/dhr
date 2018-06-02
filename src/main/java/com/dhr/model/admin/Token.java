package com.dhr.model.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@Document(collection = "tokens")
public class Token {
    @Id
    private Long tokenId;
    private String token;
    private Date creationDate;
}
