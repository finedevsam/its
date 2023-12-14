package com.yorksj.itsproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public ResponseEntity<?> landing(){
        Map<String, Object> data = new HashMap<>();
        data.put("version", "V1");
        data.put("message", "welcome to Intelligence Tutoring System");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
