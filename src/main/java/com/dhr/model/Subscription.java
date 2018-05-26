package com.dhr.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@ToString
@Document(collection = "subscriptions")
public class Subscription {
    private Date startDate;
    private Date endDate;
    private String type;
}
