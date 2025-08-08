package com.ahsumon.walletservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TransferToBankResponse {

    private boolean success;


    private String message;


    private Long transactionId;
}
