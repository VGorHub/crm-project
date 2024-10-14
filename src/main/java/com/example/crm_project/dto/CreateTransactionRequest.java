package com.example.crm_project.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateTransactionRequest {

    @NotNull(message = "ID продавца не может быть пустым")
    private Long sellerId;

    @NotNull(message = "Сумма транзакции не может быть пустой")
    @DecimalMin(value = "0.0", inclusive = false, message = "Сумма должна быть больше 0")
    private BigDecimal amount;

    @NotNull(message = "Тип оплаты не может быть пустым")
    private String paymentType;
}
