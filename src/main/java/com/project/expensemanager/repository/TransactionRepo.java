package com.project.expensemanager.repository;

import com.project.expensemanager.domain.TransactionRecord;
import com.project.expensemanager.domain.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionRecord, Integer> {
    List<TransactionRecord> findAllByUsername(String username);

    List<TransactionRecord> findByUsernameAndTransactionType(String username, TransactionType transactionType);
}
