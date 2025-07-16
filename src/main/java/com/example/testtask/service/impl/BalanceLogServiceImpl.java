package com.example.testtask.service.impl;

import com.example.testtask.model.Balance;
import com.example.testtask.model.BalanceLog;
import com.example.testtask.model.Transaction;
import com.example.testtask.model.enums.BalanceLogType;
import com.example.testtask.repository.BalanceLogRepository;
import com.example.testtask.service.BalanceLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceLogServiceImpl implements BalanceLogService {

    private final BalanceLogRepository balanceLogRepository;

    @Override
    public void log(Transaction transaction, Balance balance) {
        BalanceLog log = BalanceLog.builder()
                .user(transaction.getUser())
                .balance(balance)
                .transaction(transaction)
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .balanceType(transaction.getBalanceType())
                .logType(transaction.getAmount() >= 0 ? BalanceLogType.INCOME : BalanceLogType.OUTCOME)
                .createdAt(transaction.getCreatedAt())
                .build();

        balanceLogRepository.save(log);
    }
}
