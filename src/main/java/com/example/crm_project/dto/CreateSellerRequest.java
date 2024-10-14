package com.example.crm_project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание продавца")
public class CreateSellerRequest {

    @NotBlank(message = "Имя продавца не может быть пустым")
    @Size(max = 100, message = "Имя продавца не должно превышать 100 символов")
    @Schema(description = "Имя продавца", example = "Иван Иванов")
    private String name;

    @Size(max = 255, message = "Контактная информация не должна превышать 255 символов")
    @Schema(description = "Контактная информация", example = "+7 (123) 456-78-90")
    private String contactInfo;
}
