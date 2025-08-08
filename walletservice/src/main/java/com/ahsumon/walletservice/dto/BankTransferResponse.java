package com.ahsumon.walletservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankTransferResponse {


    private boolean success;

    private String message;

    private Long transactionId;
}
