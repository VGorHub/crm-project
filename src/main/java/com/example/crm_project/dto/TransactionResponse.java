package com.example.crm_project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Ответ с информацией о транзакции")
public class TransactionResponse {

    @Schema(description = "Уникальный идентификатор транзакции", example = "1")
    private Long id;

    @Schema(description = "ID продавца", example = "1")
    private Long sellerId;

    @Schema(description = "Сумма транзакции", example = "1500.00")
    private BigDecimal amount;

    @Schema(description = "Тип оплаты (CASH, CARD, TRANSFER)", example = "CARD")
    private String paymentType;

    @Schema(description = "Дата и время транзакции", example = "2024-10-15T12:45:00")
    private String transactionDate;
}
