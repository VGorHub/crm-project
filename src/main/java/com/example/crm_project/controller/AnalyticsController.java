package com.example.crm_project.controller;

import com.example.crm_project.dto.BestPeriodResponse;
import com.example.crm_project.dto.SellerWithTotalAmountResponse;
import com.example.crm_project.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
@Tag(name = "Аналитика", description = "API для аналитических данных")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @Operation(
            summary = "Получить самого продуктивного продавца за период",
            parameters = {
                    @Parameter(name = "period", description = "Период времени (day, month, quarter, year)", required = true, example = "month")
            }
    )
    @GetMapping("/top-seller")
    public ResponseEntity<SellerWithTotalAmountResponse> getTopSeller(@RequestParam String period) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate;

        switch (period.toLowerCase()) {
            case "day":
                startDate = now.minusDays(1);
                break;
            case "month":
                startDate = now.minusMonths(1);
                break;
            case "quarter":
                startDate = now.minusMonths(3);
                break;
            case "year":
                startDate = now.minusYears(1);
                break;
            default:
                return ResponseEntity.badRequest().build();
        }

        return analyticsService.getTopSeller(startDate, now)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Получить список продавцов с суммой транзакций меньше указанной за период",
            parameters = {
                    @Parameter(name = "amount", description = "Максимальная сумма транзакций", required = true, example = "10000.00"),
                    @Parameter(name = "startDate", description = "Дата начала периода в формате ISO", required = true, example = "2024-01-01T00:00:00"),
                    @Parameter(name = "endDate", description = "Дата окончания периода в формате ISO", required = true, example = "2024-12-31T23:59:59")
            }
    )
    @GetMapping("/underperforming-sellers")
    public ResponseEntity<List<SellerWithTotalAmountResponse>> getSellersWithTotalAmountLessThan(
            @RequestParam BigDecimal amount,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<SellerWithTotalAmountResponse> sellers = analyticsService.getSellersWithTotalAmountLessThan(amount, startDate, endDate);
        return ResponseEntity.ok(sellers);
    }

    @Operation(
            summary = "Получить наилучший период времени для продавца",
            parameters = {
                    @Parameter(name = "sellerId", description = "ID продавца", required = true, example = "1")
            }
    )
    @GetMapping("/best-period/{sellerId}")
    public ResponseEntity<BestPeriodResponse> getBestPeriodForSeller(@PathVariable Long sellerId) {
        BestPeriodResponse result = analyticsService.getBestPeriodForSeller(sellerId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
