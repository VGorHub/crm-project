package com.example.crm_project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Ответ с информацией о продавце и сумме его транзакций за период")
public class SellerWithTotalAmountResponse {

    @Schema(description = "Уникальный идентификатор продавца", example = "1")
    private Long id;

    @Schema(description = "Имя продавца", example = "Иван Иванов")
    private String name;

    @Schema(description = "Контактная информация продавца", example = "+7 (123) 456-78-90")
    private String contactInfo;

    @Schema(description = "Дата регистрации продавца", example = "2023-10-15T12:34:56")
    private String registrationDate;

    @Schema(description = "Сумма транзакций продавца за период", example = "15000.00")
    private BigDecimal totalAmount;
}
