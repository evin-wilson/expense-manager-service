package com.project.expensemanager.controller;

import com.project.expensemanager.domain.TransactionType;
import com.project.expensemanager.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping(value = "/transactions/{username}", produces = APPLICATION_JSON_VALUE)
    public void getAllTransactions(@PathVariable String username,
                                   @RequestParam("date") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date,
                                   @RequestParam(required = false) TransactionType transactionType) {
        transactionService.getAllTransactions(username, date, transactionType);
    }
}
