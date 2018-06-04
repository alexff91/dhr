package com.dhr.model.admin;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthData implements Serializable {
    private String username;
    private String password;
}
