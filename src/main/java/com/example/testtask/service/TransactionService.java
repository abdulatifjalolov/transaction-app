package com.example.testtask.service;

import com.example.testtask.dto.TransactionDTO;
import com.example.testtask.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    Transaction create(TransactionDTO dto);
    List<Transaction> getAll(Long userId, LocalDateTime from, LocalDateTime to);
}