package com.example.testtask.repository;

import com.example.testtask.model.Balance;
import com.example.testtask.model.User;
import com.example.testtask.model.enums.BalanceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    Optional<Balance> findByUserAndType(User user, BalanceType type);
}
