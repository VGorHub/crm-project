package com.example.crm_project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Ответ с информацией о наилучшем периоде продавца")
public class BestPeriodResponse {

    @Schema(description = "ID продавца", example = "1")
    private Long sellerId;

    @Schema(description = "Количество транзакций в периоде", example = "10")
    private int transactionCount;

    @Schema(description = "Дата начала периода", example = "2023-10-01T00:00:00")
    private LocalDateTime startDate;

    @Schema(description = "Дата окончания периода", example = "2023-10-15T23:59:59")
    private LocalDateTime endDate;
}
