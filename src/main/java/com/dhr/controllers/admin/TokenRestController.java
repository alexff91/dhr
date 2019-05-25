package com.dhr.controllers.admin;

import com.dhr.model.admin.AuthData;
import com.dhr.model.admin.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/tokens")
public class TokenRestController {
    @PostMapping
    public ResponseEntity<Token> generateToken(@RequestBody AuthData authData) {
        return new ResponseEntity<>(Token.builder().token("sometoken").build(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<String> validateToken() {
        return new ResponseEntity<>("{\"message\":\"Token is validated.\"}", HttpStatus.OK);
    }
}