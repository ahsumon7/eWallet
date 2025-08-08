package com.ahsumon.bankservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletRequest {

    /**
     * Unique identifier of the wallet.
     */
    @NotBlank(message = "Wallet ID must not be blank")
    private String walletId;

    /**
     * Amount of money to credit or debit.
     */
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    /**
     * Optional description for the transaction.
     */
    private String description;
}
