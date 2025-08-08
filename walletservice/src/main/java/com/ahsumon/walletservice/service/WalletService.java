package com.ahsumon.walletservice.service;


import com.ahsumon.walletservice.client.BankServiceClient;
import com.ahsumon.walletservice.dto.*;
import com.ahsumon.walletservice.entity.Wallet;
import com.ahsumon.walletservice.entity.WalletTransaction;
import com.ahsumon.walletservice.enums.WalletTransactionStatus;
import com.ahsumon.walletservice.enums.WalletTransactionType;
import com.ahsumon.walletservice.repository.WalletRepository;
import com.ahsumon.walletservice.repository.WalletTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletTransactionRepository transactionRepository;


    @Autowired
    private BankServiceClient bankServiceClient;

    @Transactional
    public WalletResponse creditWallet(WalletRequest request) {
        try {
            // Find and lock the wallet
            Wallet wallet = walletRepository.findByWalletIdWithLock(request.getWalletId())
                    .orElseThrow(() -> new RuntimeException("Wallet not found"));

            // Create transaction record
            WalletTransaction transaction = new WalletTransaction();
            transaction.setWalletId(request.getWalletId());
            transaction.setAmount(request.getAmount());
            transaction.setType(WalletTransactionType.CREDIT);
            transaction.setDescription(request.getDescription());
            //transaction.setReferenceId(request.getReferenceId());
            transaction = transactionRepository.save(transaction);

            // Credit to wallet
            wallet.setBalance(wallet.getBalance().add(request.getAmount()));
            walletRepository.save(wallet);

            transaction.setStatus(WalletTransactionStatus.COMPLETED);
            transactionRepository.save(transaction);

            return new WalletResponse(true, "Wallet credited successfully", transaction.getId());

        } catch (Exception e) {
            return new WalletResponse(false, "Credit failed: " + e.getMessage(), null);
        }
    }

    @Transactional
    public WalletResponse debitWallet(WalletRequest request) {
        try {
            // Find and lock the wallet
            Wallet wallet = walletRepository.findByWalletIdWithLock(request.getWalletId())
                    .orElseThrow(() -> new RuntimeException("Wallet not found"));

            // Check sufficient balance
            if (wallet.getBalance().compareTo(request.getAmount()) < 0) {
                return new WalletResponse(false, "Insufficient wallet balance", null);
            }

            // Create transaction record
            WalletTransaction transaction = new WalletTransaction();
            transaction.setWalletId(request.getWalletId());
            transaction.setAmount(request.getAmount());
            transaction.setType(WalletTransactionType.DEBIT);
            transaction.setDescription(request.getDescription());
            //transaction.setReferenceId(request.getReferenceId());
            transaction = transactionRepository.save(transaction);

            // Debit from wallet
            wallet.setBalance(wallet.getBalance().subtract(request.getAmount()));
            walletRepository.save(wallet);

            transaction.setStatus(WalletTransactionStatus.COMPLETED);
            transactionRepository.save(transaction);

            return new WalletResponse(true, "Wallet debited successfully", transaction.getId());

        } catch (Exception e) {
            return new WalletResponse(false, "Debit failed: " + e.getMessage(), null);
        }
    }

    @Transactional
    public WalletResponse transferToBank(TransferToBankRequest request) {
        try {
            // Find and lock the wallet
            Wallet wallet = walletRepository.findByWalletIdWithLock(request.getWalletId())
                    .orElseThrow(() -> new RuntimeException("Wallet not found"));

            // Check sufficient balance
            if (wallet.getBalance().compareTo(request.getAmount()) < 0) {
                return new WalletResponse(false, "Insufficient wallet balance", null);
            }

            // Create transaction record
            WalletTransaction transaction = new WalletTransaction();
            transaction.setWalletId(request.getWalletId());
            transaction.setAmount(request.getAmount());
            transaction.setType(WalletTransactionType.TRANSFER_TO_BANK);
            transaction.setDescription(request.getDescription());
            transaction = transactionRepository.save(transaction);

            // Debit from wallet
            wallet.setBalance(wallet.getBalance().subtract(request.getAmount()));
            walletRepository.save(wallet);

            // Transfer to bank via API call
            BankTransferRequest bankRequest = new BankTransferRequest(
                    request.getWalletId(),
                    request.getBankAccount(),
                    request.getAmount(),
                    "Transfer from wallet: " + request.getWalletId()
            );

            BankTransferResponse bankResponse = bankServiceClient.receiveFromWallet(bankRequest);

            if (bankResponse.isSuccess()) {
                transaction.setStatus(WalletTransactionStatus.COMPLETED);
                transactionRepository.save(transaction);
                return new WalletResponse(true, "Transfer to bank successful", transaction.getId());
            } else {
                // Rollback wallet debit
                wallet.setBalance(wallet.getBalance().add(request.getAmount()));
                walletRepository.save(wallet);
                transaction.setStatus(WalletTransactionStatus.FAILED);
                transactionRepository.save(transaction);
                return new WalletResponse(false, "Transfer to bank failed: " + bankResponse.getMessage(), transaction.getId());
            }

        } catch (Exception e) {
            return new WalletResponse(false, "Transfer failed: " + e.getMessage(), null);
        }
    }
}
