package com.example.testtask.model;

import com.example.testtask.model.enums.BalanceLogType;
import com.example.testtask.model.enums.BalanceType;
import com.example.testtask.utils.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "balance_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceLog extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private BalanceLogType logType;

    @Enumerated(EnumType.STRING)
    private BalanceType balanceType;

    private LocalDateTime createdAt;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Balance balance;

    @ManyToOne(fetch = FetchType.LAZY)
    private Transaction transaction;
}

