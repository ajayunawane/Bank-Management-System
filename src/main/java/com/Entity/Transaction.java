package com.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private String transactionType;
    private Double amount;
    private Double balanceAfterTransaction;

    private LocalDateTime transactionDate;

    private String description;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
