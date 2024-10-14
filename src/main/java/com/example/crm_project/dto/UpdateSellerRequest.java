package com.example.crm_project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на обновление информации о продавце")
public class UpdateSellerRequest {

    @NotBlank(message = "Имя продавца не может быть пустым")
    @Size(max = 100, message = "Имя продавца не должно превышать 100 символов")
    @Schema(description = "Новое имя продавца", example = "Пётр Петров")
    private String name;

    @Size(max = 255, message = "Контактная информация не должна превышать 255 символов")
    @Schema(description = "Новая контактная информация", example = "+7 (987) 654-32-10")
    private String contactInfo;
}
