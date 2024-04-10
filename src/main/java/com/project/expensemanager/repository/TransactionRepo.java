package com.project.expensemanager.repository;

import com.project.expensemanager.domain.TransactionRecord;
import com.project.expensemanager.domain.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionRepo extends JpaRepository<TransactionRecord, Integer> {
    @Query(value = "select * from transactions  where username = :username", nativeQuery = true)
    List<TransactionRecord> findAllByUsername(String username);

    List<TransactionRecord> findByUsernameAndTransactionType(String username, TransactionType transactionType);
}
