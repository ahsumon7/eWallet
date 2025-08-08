package com.ahsumon.walletservice.client;


import com.ahsumon.walletservice.dto.BankTransferRequest;
import com.ahsumon.walletservice.dto.BankTransferResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "bank-service", url = "${bank.service.url:http://localhost:8081}")
public interface BankServiceClient {

    @PostMapping("/api/bank/receive-from-wallet")
    BankTransferResponse receiveFromWallet(@RequestBody BankTransferRequest bankTransferRequest);

}
