package com.ahsumon.bankservice.controller;

import com.ahsumon.bankservice.dto.TransferRequest;
import com.ahsumon.bankservice.dto.TransferResponse;
import com.ahsumon.bankservice.entity.Account;
import com.ahsumon.bankservice.repository.AccountRepository;
import com.ahsumon.bankservice.service.BankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Bank API", description = "APIs related to bank account management and transfers")
@RestController
@RequestMapping("/api/bank")
public class BankController {

    @Autowired
    private BankService bankService;

    @Autowired
    private AccountRepository accountRepository;

    @Operation(summary = "Transfer money from bank to wallet")
    @PostMapping("/transfer-to-wallet")
    public ResponseEntity<TransferResponse> transferToWallet(@RequestBody TransferRequest request) {
        TransferResponse response = bankService.transferToWallet(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Receive money from wallet to bank")
    @PostMapping("/receive-from-wallet")
    public ResponseEntity<TransferResponse> receiveFromWallet(@RequestBody TransferRequest request) {
        TransferResponse response = bankService.receiveFromWallet(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all bank accounts")
    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountRepository.findAll());
    }

    @Operation(summary = "Get a specific bank account by account number")
    @GetMapping("/accounts/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new bank account")
    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account savedAccount = accountRepository.save(account);
        return ResponseEntity.ok(savedAccount);
    }
}
