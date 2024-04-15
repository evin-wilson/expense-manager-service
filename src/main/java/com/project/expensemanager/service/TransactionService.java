package com.project.expensemanager.service;

import com.project.expensemanager.domain.Summary;
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
import java.time.format.TextStyle;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

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
            return transactionRepo.findByUsernameAndDateAndTransactionType(username, date, transactionType.name())
                    .orElseThrow(() -> new EntityNotFoundException("No transaction for the given date and tranction Type"));
        }
        return transactionRepo.findAllByUsernameAndTransactionAt(username, date)
                .orElseThrow(() -> new EntityNotFoundException("No transaction for the given date"));
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

    public Summary totalAmount(String username, Short year) {
        List<TransactionRecord> transactionRecords;
        validateUser(username);
        transactionRecords = transactionRepo.findByUsernameAndYear(username, year)
                .orElseThrow(() -> new EntityNotFoundException("No transaction done for given year"));
        return generateSummary(transactionRecords);
    }

    private Summary generateSummary(List<TransactionRecord> transactionRecords) {
        // find the income and expense month wise
        Map<String, Map<TransactionType, Double>> monthlyTransactionSummary = transactionRecords.stream()
                .collect(groupingBy(
                        t -> t.getTransactionAt().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                        groupingBy(
                                TransactionRecord::getTransactionType,
                                Collectors.summingDouble(TransactionRecord::getAmount)
                        )
                ));
        List<Summary.MonthSummary> monthSummaryList = monthlyTransactionSummary.entrySet().stream()
                .map(entry -> {
                    BigDecimal expense = BigDecimal.valueOf(entry.getValue().getOrDefault(TransactionType.EXPENSE, 0.0));
                    BigDecimal income = BigDecimal.valueOf(entry.getValue().getOrDefault(TransactionType.INCOME, 0.0));
                    return Summary.MonthSummary.builder()
                            .expense(expense)
                            .income(income)
                            .month(entry.getKey())
                            .build();
                })
                .collect(Collectors.toList());

        // find the annual income and expense for the given year
        Map<TransactionType, BigDecimal> annualTransactions = transactionRecords.stream()
                .collect(groupingBy(TransactionRecord::getTransactionType,
                        Collectors.reducing(BigDecimal.ZERO, tr -> BigDecimal.valueOf(tr.getAmount()), BigDecimal::add)));

        return Summary.builder()
                .annualExpense(annualTransactions.get(TransactionType.EXPENSE))
                .annualIncome(annualTransactions.get(TransactionType.INCOME))
                .monthSummary(monthSummaryList)
                .build();

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
