package com.project.expensemanager.controller;

import com.project.expensemanager.domain.Summary;
import com.project.expensemanager.domain.TransactionRecord;
import com.project.expensemanager.domain.TransactionType;
import com.project.expensemanager.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/transactions/{username}")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<TransactionRecord> getAllTransactions(@PathVariable String username,
                                                      @RequestParam(required = false) Date date,
                                                      @RequestParam(required = false) Short year,
                                                      @RequestParam(required = false) Short month,
                                                      @RequestParam(required = false) TransactionType transactionType) {
        if (date == null && year == null & month == null) {
            throw new IllegalArgumentException("At least one of date, year, or month must be provided !");
        }
        return transactionService.getAllTransactions(username, date, transactionType);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addTransaction(@PathVariable String username,
                                                 @RequestBody TransactionRecord transactionRecord) {
        transactionService.addTransaction(username, transactionRecord);
        return ResponseEntity.ok("{\"message\":\"New transaction added successfully\"}");
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTransaction(@PathVariable String username,
                                                    @RequestParam int id,
                                                    @RequestBody TransactionRecord transactionRecord) {
        transactionService.updateTransaction(username, id, transactionRecord);
        return ResponseEntity.ok("{\"message\":\"Transaction updated successfully\"}");
    }

    @DeleteMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteTransaction(@PathVariable String username,
                                                    @RequestParam int id) {
        transactionService.deleteTransaction(username, id);
        return ResponseEntity.ok("{\"message\":\"Transaction deleted successfully\"}");
    }

    @GetMapping(value = "/summary", produces = APPLICATION_JSON_VALUE)
    public Summary getTotalAmount(@PathVariable String username,
                                  @RequestParam Short year) {
        if (year == null) {
            throw new IllegalArgumentException("At least year or month must be provided !");
        }
        return transactionService.totalAmount(username, year);
    }
}
