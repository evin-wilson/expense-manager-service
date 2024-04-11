package com.project.expensemanager.service;

import com.project.expensemanager.domain.TransactionRecord;
import com.project.expensemanager.domain.TransactionType;
import com.project.expensemanager.repository.TransactionRepo;
import com.project.expensemanager.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class TransactionService {

    private final TransactionRepo transactionRepo;
    private final UserRepo userRepo;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public TransactionService(TransactionRepo transactionRepo, UserRepo userRepo) {
        this.transactionRepo = transactionRepo;
        this.userRepo = userRepo;
    }

    public List<TransactionRecord> getAllTransactions(String username, LocalDate date, TransactionType transactionType) {
        validateUser(username);
        if (transactionType != null) {
            return transactionRepo.findByUsernameAndDateAndTransactionType(username, date, transactionType);
        }
        return transactionRepo.findAllByUsernameAndDate(username, date);
    }

    public void addTransaction(String username, TransactionRecord transactionRecord) {
        validateUser(username);
        transactionRepo.save(transactionRecord);
    }

    public void deleteTransaction(String username, int id) {
        validateUser(username);
        transactionRepo.deleteById(id);
    }

    private void validateUser(String username) {
        userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found !"));
    }
}
