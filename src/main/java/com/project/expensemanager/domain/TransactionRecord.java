package com.project.expensemanager.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class TransactionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @CreationTimestamp
    private Timestamp createdAt;

    private String username;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private Float amount;
    private String account;
    private String category;
    private String subCategory;
    private Timestamp transactionAt;
    private String description;
}
