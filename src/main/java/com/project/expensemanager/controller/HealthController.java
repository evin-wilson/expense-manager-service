package com.project.expensemanager.controller;

import com.project.expensemanager.domain.Health;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class HealthController {

    @GetMapping(value = "/health", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Health> heath(){
        return new ResponseEntity<>(Health.builder().version(1).status("Ok").build(), OK);
    }
}
