package com.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@Table(name="customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String fullName;

    private LocalDate dateOfBirth;

    private String gender;

    private String email;

    private String contactNo;

    private String address;

    private String city;
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account account;
}
