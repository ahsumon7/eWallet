package com.ahsumon.bankservice.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WalletResponse represents the response received after a wallet operation,
 * such as credit or debit.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletResponse {

    /**
     * Indicates whether the wallet operation was successful.
     */
    private boolean success;

    /**
     * Provides additional information or error message.
     */
    private String message;

    /**
     * The unique identifier of the transaction created by the operation.
     * Can be null if the operation failed or transaction is not created.
     */
    private Long transactionId;
}
