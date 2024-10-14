package com.example.crm_project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Запрос на создание транзакции")
public class CreateTransactionRequest {

    @NotNull(message = "ID продавца не может быть пустым")
    @Schema(description = "ID продавца", example = "1")
    private Long sellerId;

    @NotNull(message = "Сумма транзакции не может быть пустой")
    @DecimalMin(value = "0.0", inclusive = false, message = "Сумма должна быть больше 0")
    @Schema(description = "Сумма транзакции", example = "1500.00")
    private BigDecimal amount;

    @NotNull(message = "Тип оплаты не может быть пустым")
    @Schema(description = "Тип оплаты (CASH, CARD, TRANSFER)", example = "CARD")
    private String paymentType;
}
