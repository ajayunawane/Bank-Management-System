package com.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    @Column(unique = true,nullable = false)
    private String accountNo;
    private String accountType;
    private Double balance;
    private LocalDate accountCreateDate;
    private String branch;
    private String ifscCode;
    private String status;
    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;
//   @JsonIgnore
    @OneToOne(mappedBy = "account")
    private Customer customer;
}
