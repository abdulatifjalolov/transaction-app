package com.example.testtask.repository;

import com.example.testtask.model.BalanceLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceLogRepository extends JpaRepository<BalanceLog, Long> {
}
