package com.ahsumon.bankservice.client;

import com.ahsumon.bankservice.dto.WalletRequest;
import com.ahsumon.bankservice.dto.WalletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "wallet-service", url = "${wallet.service.url:http://localhost:8082}")
public interface WalletServiceClient {

    @PostMapping("/api/wallet/credit")
    WalletResponse creditWallet(@RequestBody WalletRequest request);

    @PostMapping("/api/wallet/debit")
    WalletResponse debitWallet(@RequestBody WalletRequest request);
}
