package com.example.crm_project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Ответ с информацией о продавце")
public class SellerResponse {

    @Schema(description = "Уникальный идентификатор продавца", example = "1")
    private Long id;

    @Schema(description = "Имя продавца", example = "Иван Иванов")
    private String name;

    @Schema(description = "Контактная информация продавца", example = "+7 (123) 456-78-90")
    private String contactInfo;

    @Schema(description = "Дата регистрации продавца", example = "2024-10-15T12:34:56")
    private String registrationDate;
}
