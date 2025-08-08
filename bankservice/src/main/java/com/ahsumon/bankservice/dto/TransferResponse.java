package com.ahsumon.bankservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TransferResponse is used to send the response of a transfer operation.
 * It contains status, message, and transaction identifier.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferResponse {

    /**
     * Indicates if the transfer was successful or not.
     */
    private boolean success;

    /**
     * Human-readable message about the transfer result.
     */
    private String message;

    /**
     * Unique identifier of the created transaction, if applicable.
     * Can be null if transaction not created or on failure.
     */
    private Long transactionId;
}
