package com.example.testtask.repository;

import com.example.testtask.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    List<Transaction> findAllByUserId(Long userId);

    List<Transaction> findAllByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    List<Transaction> findAllByUserIdAndCreatedAtBetween(Long userId, LocalDateTime from, LocalDateTime to);
}