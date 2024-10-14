package com.example.crm_project.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionResponse {

    private Long id;
    private Long sellerId;
    private BigDecimal amount;
    private String paymentType;
    private String transactionDate;
}
