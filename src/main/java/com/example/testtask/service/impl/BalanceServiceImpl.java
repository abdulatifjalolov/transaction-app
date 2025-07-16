package com.example.testtask.service.impl;

import com.example.testtask.model.Balance;
import com.example.testtask.model.User;
import com.example.testtask.model.enums.BalanceType;
import com.example.testtask.repository.BalanceRepository;
import com.example.testtask.service.BalanceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;

    @Override
    public Balance findOrCreateMainBalance(User user) {
        return balanceRepository.findByUserAndType(user, BalanceType.MAIN)
                .orElseGet(() -> {
                    Balance balance = Balance.builder()
                            .user(user)
                            .type(BalanceType.MAIN)
                            .amount(0.0)
                            .build();
                    return balanceRepository.save(balance);
                });
    }

    @Override
    @Transactional
    public Balance updateMainBalance(User user, Double delta) {
        Balance balance = findOrCreateMainBalance(user);
        balance.setAmount(balance.getAmount() + delta);
        return balanceRepository.save(balance);
    }
}

