package com.example.crm_project.controller;

import com.example.crm_project.dto.BestPeriodResponse;
import com.example.crm_project.dto.SellerWithTotalAmountResponse;
import com.example.crm_project.service.AnalyticsService;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@WebMvcTest(AnalyticsController.class)
class AnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnalyticsService analyticsService;

    @Test
    void testGetTopSeller() throws Exception {
        SellerWithTotalAmountResponse response = new SellerWithTotalAmountResponse();
        response.setId(1L);
        response.setName("Иван Иванов");
        response.setTotalAmount(new BigDecimal("5000"));

        when(analyticsService.getTopSeller(any(), any())).thenReturn(java.util.Optional.of(response));

        mockMvc.perform(get("/analytics/top-seller?period=month"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Иван Иванов"))
                .andExpect(jsonPath("$.totalAmount").value(5000));
    }

    @Test
    void testGetSellersWithTotalAmountLessThan() throws Exception {
        SellerWithTotalAmountResponse response1 = new SellerWithTotalAmountResponse();
        response1.setId(1L);
        response1.setName("Иван Иванов");
        response1.setTotalAmount(new BigDecimal("5000"));

        SellerWithTotalAmountResponse response2 = new SellerWithTotalAmountResponse();
        response2.setId(2L);
        response2.setName("Петр Петров");
        response2.setTotalAmount(new BigDecimal("3000"));

        when(analyticsService.getSellersWithTotalAmountLessThan(any(), any(), any())).thenReturn(Arrays.asList(response1, response2));

        mockMvc.perform(get("/analytics/underperforming-sellers")
                .param("amount", "10000")
                .param("startDate", "2024-01-01T00:00:00")
                .param("endDate", "2024-12-31T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetBestPeriodForSeller() throws Exception {
        BestPeriodResponse response = new BestPeriodResponse();
        response.setSellerId(1L);
        response.setTransactionCount(10);
        response.setStartDate(LocalDateTime.now().minusDays(10));
        response.setEndDate(LocalDateTime.now());

        when(analyticsService.getBestPeriodForSeller(1L)).thenReturn(response);

        mockMvc.perform(get("/analytics/best-period/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionCount").value(10));
    }
}
