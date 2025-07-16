package com.example.testtask.service;

import com.example.testtask.model.Balance;
import com.example.testtask.model.User;

public interface BalanceService {
    Balance findOrCreateMainBalance(User user);
    Balance updateMainBalance(User user, Double delta);
}

