package com.project.expensemanager.controller;

import com.project.expensemanager.domain.TransactionRecord;
import com.project.expensemanager.domain.TransactionType;
import com.project.expensemanager.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/transactions/{username}")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping( produces = APPLICATION_JSON_VALUE)
    public List<TransactionRecord> getAllTransactions(@PathVariable String username,
                                                      @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                                      @RequestParam(required = false) TransactionType transactionType) {
        return transactionService.getAllTransactions(username, date, transactionType);
    }

    @PostMapping(value = "/addTransaction", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addTransaction(@PathVariable String username,
                                                 @RequestBody TransactionRecord transactionRecord) {
        transactionService.addTransaction(username, transactionRecord);
        return ResponseEntity.ok("{\"message\":\"New transaction added successfully\"}");
    }

    @DeleteMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteTransaction(@PathVariable String username,
                                                    @RequestParam int id){
        transactionService.deleteTransaction(username, id);
        return ResponseEntity.ok("{\"message\":\"Transaction deleted successfully\"}");
    }
}
