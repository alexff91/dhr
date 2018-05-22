package com.dhr.controllers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class RecorderApp {
    public static void main(String[] args) {
        SpringApplication.run(RecorderApp.class, args);
    }
}
