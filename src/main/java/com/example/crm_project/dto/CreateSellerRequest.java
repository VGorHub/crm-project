package com.example.crm_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateSellerRequest {

    @NotBlank(message = "Имя продавца не может быть пустым")
    @Size(max = 100, message = "Имя продавца не должно превышать 100 символов")
    private String name;

    @Size(max = 255, message = "Контактная информация не должна превышать 255 символов")
    private String contactInfo;
}
