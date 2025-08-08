package com.ahsumon.walletservice.repository;

import com.ahsumon.walletservice.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long>{
    List<WalletTransaction> findByWalletIdOrderByCreatedAtDesc(String walletId);

}
