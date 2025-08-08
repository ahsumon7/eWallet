// Package declaration - organizes the class under a specific namespace
package com.ahsumon.bankservice.entity;

// Importing necessary JPA and Lombok annotations
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;         // For high-precision monetary values
import java.time.LocalDateTime;     // For storing date and time

// Lombok annotation that generates getters, setters, toString(), equals(), and hashCode()
@Data
// Lombok generates a no-argument constructor: public Account() {}
@NoArgsConstructor
// Lombok generates an all-argument constructor: public Account(...) {...}
@AllArgsConstructor

// Marks this class as a JPA entity, meaning it maps to a database table
@Entity
// Specifies the table name in the database that this entity maps to
@Table(name = "accounts")
public class Account {

    // Primary key of the table. Value is auto-generated (usually auto-increment in DB)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique account number (e.g., "ACC12345"), cannot be null
    @Column(unique = true, nullable = false)
    private String accountNumber;

    // Name of the person who owns the account, cannot be null
    @Column(nullable = false)
    private String accountHolderName;

    // Account balance with high precision for currency
    // precision = total digits, scale = digits after decimal
    // Example: 999999999999999.99 (17 digits before, 2 after)
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    // Timestamp for when the account was created
    // Maps to the "created_at" column in the database
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Timestamp for when the account was last updated
    // Maps to the "updated_at" column in the database
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }



}
