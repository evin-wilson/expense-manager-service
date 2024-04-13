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

import java.math.BigDecimal;
import java.sql.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


    public List<TransactionRecord> getAllTransactions(String username, Date date, TransactionType transactionType) {
        validateUser(username);
        if (transactionType != null) {
            return transactionRepo.findByUsernameAndDateAndTransactionType(username, date, transactionType.name());
        }
        return transactionRepo.findAllByUsernameAndTransactionAt(username, date);
    }

    public void addTransaction(String username, TransactionRecord transactionRecord) {
        validateUser(username);
        transactionRepo.save(transactionRecord);
    }

    public void updateTransaction(String username, int id, TransactionRecord transactionRecord) {
        validateUser(username);
        TransactionRecord existingTransactionRecord = transactionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Record not found for the given id"));
        validateTransaction(transactionRecord);

        existingTransactionRecord.setTransactionType(transactionRecord.getTransactionType() != null ? transactionRecord.getTransactionType() : existingTransactionRecord.getTransactionType());
        existingTransactionRecord.setTransactionAt(transactionRecord.getTransactionAt() != null ? transactionRecord.getTransactionAt() : existingTransactionRecord.getTransactionAt());
        existingTransactionRecord.setAccount(transactionRecord.getAccount() != null ? transactionRecord.getAccount() : existingTransactionRecord.getAccount());
        existingTransactionRecord.setAmount(transactionRecord.getAmount() != null ? transactionRecord.getAmount() : existingTransactionRecord.getAmount());
        existingTransactionRecord.setCategory(transactionRecord.getCategory() != null ? transactionRecord.getCategory() : existingTransactionRecord.getCategory());
        existingTransactionRecord.setSubCategory(transactionRecord.getSubCategory() != null ? transactionRecord.getSubCategory() : existingTransactionRecord.getSubCategory());

        transactionRepo.save(existingTransactionRecord);
    }

    public void deleteTransaction(String username, int id) {
        validateUser(username);
        transactionRepo.deleteById(id);
    }

    public Map<TransactionType, BigDecimal> totalAmount(String username, Short year, Short month) {
        List<TransactionRecord> transactionRecords;
        validateUser(username);
        if (month != null) {
            transactionRecords = transactionRepo.findByUsernameAndYearAndMonth(username, year, month);
        } else {
            transactionRecords = transactionRepo.findByUsernameAndYear(username, year);
        }
        return generateSummary(transactionRecords);
    }

    private Map<TransactionType, BigDecimal> generateSummary(List<TransactionRecord> transactionRecords) {
        return transactionRecords.stream()
                .collect(Collectors.groupingBy(TransactionRecord::getTransactionType,
                        Collectors.reducing(BigDecimal.ZERO, tr -> BigDecimal.valueOf(tr.getAmount()), BigDecimal::add)));
    }

    private void validateUser(String username) {
        userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found !"));
    }

    private void validateTransaction(TransactionRecord transactionRecord) {
        if (transactionRecord.getTransactionType() != null) {
            boolean validTransactionType = EnumSet.allOf(TransactionType.class).contains(transactionRecord.getTransactionType());
            if (!validTransactionType) {
                throw new IllegalArgumentException("Invalid transaction type");
            }
        }
//        TODO need to check category and accounts are created and valid ones
    }
}
