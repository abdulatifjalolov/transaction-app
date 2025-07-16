package com.example.testtask.service;


import com.example.testtask.model.Balance;
import com.example.testtask.model.Transaction;

public interface BalanceLogService {
    void log(Transaction transaction, Balance balance);
}
