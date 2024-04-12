package com.project.expensemanager.repository;

import com.project.expensemanager.domain.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionRecord, Integer> {

    @Query(value = "SELECT * FROM transactions WHERE username = :username AND  CAST(transaction_at AS DATE) = :date", nativeQuery = true)
    List<TransactionRecord> findAllByUsernameAndTransactionAt(String username, Date date);

    @Query(value = "SELECT * FROM transactions WHERE username = :username AND  CAST(transaction_at AS DATE) = :date and transaction_type = :transactionType", nativeQuery = true)
    List<TransactionRecord> findByUsernameAndDateAndTransactionType(String username, Date date, String transactionType);
}
