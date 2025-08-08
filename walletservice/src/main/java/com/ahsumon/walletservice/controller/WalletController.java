package com.ahsumon.walletservice.controller;

import com.ahsumon.walletservice.dto.TransferToBankRequest;
import com.ahsumon.walletservice.dto.WalletRequest;
import com.ahsumon.walletservice.dto.WalletResponse;
import com.ahsumon.walletservice.entity.Wallet;
import com.ahsumon.walletservice.entity.WalletTransaction;
import com.ahsumon.walletservice.repository.WalletRepository;
import com.ahsumon.walletservice.repository.WalletTransactionRepository;
import com.ahsumon.walletservice.service.WalletService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletTransactionRepository transactionRepository;

    @Operation(summary = "Credit wallet", description = "Credits the wallet with the given amount.")
    @PostMapping("/credit")
    public ResponseEntity<WalletResponse> creditWallet(@RequestBody WalletRequest request) {
        WalletResponse response = walletService.creditWallet(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Debit wallet", description = "Debits the wallet with the given amount.")
    @PostMapping("/debit")
    public ResponseEntity<WalletResponse> debitWallet(@RequestBody WalletRequest request) {
        WalletResponse response = walletService.debitWallet(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Transfer to bank", description = "Transfers the wallet balance to the linked bank account.")
    @PostMapping("/transfer-to-bank")
    public ResponseEntity<WalletResponse> transferToBank(@RequestBody TransferToBankRequest request) {
        WalletResponse response = walletService.transferToBank(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all wallets", description = "Fetch all wallets from the system.")
    @GetMapping("/wallets")
    public ResponseEntity<List<Wallet>> getAllWallets() {
        return ResponseEntity.ok(walletRepository.findAll());
    }

    @Operation(summary = "Get wallet by ID", description = "Fetch a specific wallet by its wallet ID.")
    @GetMapping("/wallets/{walletId}")
    public ResponseEntity<Wallet> getWallet(
            @Parameter(description = "Wallet ID") @PathVariable String walletId) {
        return walletRepository.findByWalletId(walletId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get wallet transactions", description = "Get all transactions for a specific wallet.")
    @GetMapping("/wallets/{walletId}/transactions")
    public ResponseEntity<List<WalletTransaction>> getWalletTransactions(
            @Parameter(description = "Wallet ID") @PathVariable String walletId) {
        List<WalletTransaction> transactions = transactionRepository.findByWalletIdOrderByCreatedAtDesc(walletId);
        return ResponseEntity.ok(transactions);
    }

    @Operation(summary = "Create a new wallet", description = "Creates and saves a new wallet.")
    @PostMapping("/wallets")
    public ResponseEntity<Wallet> createWallet(@RequestBody Wallet wallet) {
        Wallet savedWallet = walletRepository.save(wallet);
        return ResponseEntity.ok(savedWallet);
    }
}
