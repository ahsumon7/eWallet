package com.ahsumon.walletservice.entity;


import com.ahsumon.walletservice.enums.WalletTransactionStatus;
import com.ahsumon.walletservice.enums.WalletTransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallet_transactions")
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String walletId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private WalletTransactionType type;

    @Enumerated(EnumType.STRING)
    private WalletTransactionStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String description;
    private String referenceId;


}
