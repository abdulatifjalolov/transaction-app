package com.example.testtask.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private Long userId;
    private Double amount;
    private String description;
    private LocalDateTime createdAt;
}