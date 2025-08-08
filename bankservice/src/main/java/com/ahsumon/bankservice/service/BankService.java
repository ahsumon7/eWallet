package com.ahsumon.bankservice.service;

import com.ahsumon.bankservice.client.WalletServiceClient;
import com.ahsumon.bankservice.dto.TransferRequest;
import com.ahsumon.bankservice.dto.TransferResponse;
import com.ahsumon.bankservice.dto.WalletRequest;
import com.ahsumon.bankservice.dto.WalletResponse;
import com.ahsumon.bankservice.entity.Account;
import com.ahsumon.bankservice.entity.Transaction;
import com.ahsumon.bankservice.enums.TransactionStatus;
import com.ahsumon.bankservice.enums.TransactionType;
import com.ahsumon.bankservice.repository.AccountRepository;
import com.ahsumon.bankservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletServiceClient walletServiceClient;

    @Transactional
    public TransferResponse transferToWallet(TransferRequest request) {
        try {
            // Find and lock the bank account
            Account account = accountRepository.findByAccountNumberWithLock(request.getFromAccount())
                    .orElseThrow(() -> new RuntimeException("Bank account not found"));

            // Check sufficient balance
            if (account.getBalance().compareTo(request.getAmount()) < 0) {
                return new TransferResponse(false, "Insufficient balance", null);
            }

            // Create transaction record
            Transaction transaction = new Transaction();
            transaction.setFromAccount(request.getFromAccount());
            transaction.setToAccount(request.getToAccount());
            transaction.setAmount(request.getAmount());
            transaction.setType(TransactionType.BANK_TO_WALLET);
            transaction.setDescription(request.getDescription());
            transaction = transactionRepository.save(transaction);

            // Debit from bank account
            account.setBalance(account.getBalance().subtract(request.getAmount()));
            accountRepository.save(account);

            // Credit to wallet via API call
            WalletRequest walletRequest = new WalletRequest(
                    request.getToAccount(),
                    request.getAmount(),
                    "Transfer from bank: " + request.getFromAccount()
            );

            WalletResponse walletResponse = walletServiceClient.creditWallet(walletRequest);

            if (walletResponse.isSuccess()) {
                transaction.setStatus(TransactionStatus.COMPLETED);
                transactionRepository.save(transaction);
                return new TransferResponse(true, "Transfer successful", transaction.getId());
            } else {
                // Rollback bank debit
                account.setBalance(account.getBalance().add(request.getAmount()));
                accountRepository.save(account);
                transaction.setStatus(TransactionStatus.FAILED);
                transactionRepository.save(transaction);
                return new TransferResponse(false, "Transfer failed: " + walletResponse.getMessage(), transaction.getId());
            }

        } catch (Exception e) {
            return new TransferResponse(false, "Transfer failed: " + e.getMessage(), null);
        }
    }

    @Transactional
    public TransferResponse receiveFromWallet(TransferRequest request) {
        try {
            // Find and lock the bank account
            Account account = accountRepository.findByAccountNumberWithLock(request.getToAccount())
                    .orElseThrow(() -> new RuntimeException("Bank account not found"));

            // Create transaction record
            Transaction transaction = new Transaction();
            transaction.setFromAccount(request.getFromAccount());
            transaction.setToAccount(request.getToAccount());
            transaction.setAmount(request.getAmount());
            transaction.setType(TransactionType.WALLET_TO_BANK);
            transaction.setDescription(request.getDescription());
            transaction = transactionRepository.save(transaction);

            // Credit to bank account
            account.setBalance(account.getBalance().add(request.getAmount()));
            accountRepository.save(account);

            transaction.setStatus(TransactionStatus.COMPLETED);
            transactionRepository.save(transaction);

            return new TransferResponse(true, "Transfer successful", transaction.getId());

        } catch (Exception e) {
            return new TransferResponse(false, "Transfer failed: " + e.getMessage(), null);
        }
    }
}
