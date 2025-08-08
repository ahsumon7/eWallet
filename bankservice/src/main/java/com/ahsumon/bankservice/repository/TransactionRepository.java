package com.ahsumon.bankservice.repository;

import com.ahsumon.bankservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.fromAccount = :account " +
            "OR t.toAccount = :account ORDER BY t.createdAt DESC")

    List<Transaction> findAllAccount(@Param("account") String account);

}
