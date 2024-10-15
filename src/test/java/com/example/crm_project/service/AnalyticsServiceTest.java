package com.example.crm_project.service;

import com.example.crm_project.dto.BestPeriodResponse;
import com.example.crm_project.dto.SellerWithTotalAmountResponse;
import com.example.crm_project.model.PaymentType;
import com.example.crm_project.model.Seller;
import com.example.crm_project.model.Transaction;
import com.example.crm_project.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
class AnalyticsServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AnalyticsService analyticsService;

    @Test
    void testGetTopSeller() {
        Seller seller = new Seller();
        seller.setId(1L);
        seller.setName("Иван Иванов");
        BigDecimal totalAmount = new BigDecimal("5000");

        List<Object[]> results = new ArrayList<>();
        results.add(new Object[]{seller, totalAmount});

        when(transactionRepository.findTotalAmountBySellerInPeriod(any(), any())).thenReturn(results);

        Optional<SellerWithTotalAmountResponse> response = analyticsService.getTopSeller(LocalDateTime.now().minusDays(30), LocalDateTime.now());

        assertTrue(response.isPresent());
        assertEquals("Иван Иванов", response.get().getName());
        assertEquals(new BigDecimal("5000"), response.get().getTotalAmount());
    }

    @Test
    void testGetSellersWithTotalAmountLessThan() {
        Seller seller1 = new Seller();
        seller1.setId(1L);
        seller1.setName("Иван Иванов");

        Seller seller2 = new Seller();
        seller2.setId(2L);
        seller2.setName("Петр Петров");

        List<Object[]> results = new ArrayList<>();
        results.add(new Object[]{seller1, new BigDecimal("5000")});
        results.add(new Object[]{seller2, new BigDecimal("3000")});

        when(transactionRepository.findSellersWithTotalAmountLessThan(any(), any(), any())).thenReturn(results);

        List<SellerWithTotalAmountResponse> responses = analyticsService.getSellersWithTotalAmountLessThan(new BigDecimal("10000"), LocalDateTime.now().minusDays(30), LocalDateTime.now());

        assertEquals(2, responses.size());
    }

    @Test
    void testGetBestPeriodForSeller() {
        Seller seller = new Seller();
        seller.setId(1L);
        seller.setName("Иван Иванов");
        List<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1L, seller, new BigDecimal("1000"), PaymentType.CARD, LocalDateTime.now().minusDays(5)));
        transactions.add(new Transaction(2L, seller, new BigDecimal("1500"), PaymentType.CARD, LocalDateTime.now().minusDays(4)));
        transactions.add(new Transaction(3L, seller, new BigDecimal("2000"), PaymentType.CARD, LocalDateTime.now().minusDays(3)));

        when(transactionRepository.findBySellerIdOrderByTransactionDateAsc(1L)).thenReturn(transactions);

        BestPeriodResponse response = analyticsService.getBestPeriodForSeller(1L);

        assertNotNull(response);
        assertEquals(3, response.getTransactionCount());
    }
}
