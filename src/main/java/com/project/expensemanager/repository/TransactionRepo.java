package com.project.expensemanager.repository;

import com.project.expensemanager.domain.TransactionRecord;
import com.project.expensemanager.domain.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionRecord, Integer> {

    @Query(value = "SELECT * FROM transactions WHERE username = :username AND  CAST(date AS DATE) = :date", nativeQuery = true)
    List<TransactionRecord> findAllByUsernameAndDate(String username, LocalDate date);

    @Query(value = "SELECT * FROM transactions WHERE username = :username AND  CAST(date AS DATE) = :date and transaction_type = :transactionType", nativeQuery = true)
    List<TransactionRecord> findByUsernameAndDateAndTransactionType(String username, LocalDate date, TransactionType transactionType);
}
