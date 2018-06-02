package com.dhr.controllers.admin;

import com.dhr.model.admin.AuthData;
import com.dhr.model.admin.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.OPTIONS, RequestMethod.GET
        , RequestMethod.POST})
@RequestMapping("/api/v1/tokens")
public class TokenRestController {
    @PostMapping
    public ResponseEntity<Token> generateToken(@RequestBody AuthData authData) {
        return new ResponseEntity<>(Token.builder().token("hello-pussy").build(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<String> validateToken() {
        return new ResponseEntity<>("{\"message\":\"Token is validated.\"}", HttpStatus.OK);
    }
}