package com.project.expensemanager.service;

import com.project.expensemanager.domain.TransactionRecord;
import com.project.expensemanager.domain.TransactionType;
import com.project.expensemanager.repository.TransactionRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    TransactionRepo transactionRepo;

    public List<TransactionRecord> getAllTransactions(String username, LocalDate date, TransactionType transactionType) {
        if (transactionType != null) {
            return transactionRepo.findByUsernameAndTransactionType(username, transactionType);
        }
        return  transactionRepo.findAllByUsername(username);
    }
}
