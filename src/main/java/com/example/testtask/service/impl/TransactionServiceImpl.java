package com.example.testtask.service.impl;

import com.example.testtask.dto.TransactionDTO;
import com.example.testtask.exception.UserNotFoundException;
import com.example.testtask.model.Balance;
import com.example.testtask.model.Transaction;
import com.example.testtask.model.User;
import com.example.testtask.model.enums.BalanceType;
import com.example.testtask.repository.TransactionRepository;
import com.example.testtask.repository.UserRepository;
import com.example.testtask.repository.spec.TransactionSpecification;
import com.example.testtask.service.BalanceLogService;
import com.example.testtask.service.BalanceService;
import com.example.testtask.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BalanceService balanceService;
    private final BalanceLogService balanceLogService;

    @Override
    @Transactional
    public Transaction create(TransactionDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(dto.getUserId().toString()));

        Balance balance = balanceService.updateMainBalance(user, dto.getAmount());

        Transaction transaction = Transaction.builder()
                .user(user)
                .amount(dto.getAmount())
                .description(dto.getDescription())
                .createdAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now())
                .balance(balance)
                .balanceType(BalanceType.MAIN)
                .build();
        Transaction savedTransaction = transactionRepository.save(transaction);
        balanceLogService.log(savedTransaction, balance);
        return savedTransaction;
    }

    @Override
    public List<Transaction> getAll(Long userId, LocalDateTime from, LocalDateTime to) {
        return transactionRepository.findAll(TransactionSpecification.filter(userId, from, to));
    }
}

