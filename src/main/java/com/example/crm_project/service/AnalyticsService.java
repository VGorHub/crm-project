package com.example.crm_project.service;

import com.example.crm_project.dto.BestPeriodResponse;
import com.example.crm_project.dto.SellerWithTotalAmountResponse;
import com.example.crm_project.model.Seller;
import com.example.crm_project.model.Transaction;
import com.example.crm_project.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final TransactionRepository transactionRepository;

    public Optional<SellerWithTotalAmountResponse> getTopSeller(LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> results = transactionRepository.findTotalAmountBySellerInPeriod(startDate, endDate);
        if (!results.isEmpty()) {
            Object[] topResult = results.get(0);
            Seller seller = (Seller) topResult[0];
            BigDecimal totalAmount = (BigDecimal) topResult[1];

            SellerWithTotalAmountResponse response = new SellerWithTotalAmountResponse();
            response.setId(seller.getId());
            response.setName(seller.getName());
            response.setContactInfo(seller.getContactInfo());
            response.setRegistrationDate(seller.getRegistrationDate().toString());
            response.setTotalAmount(totalAmount);

            return Optional.of(response);
        }
        return Optional.empty();
    }

    public List<SellerWithTotalAmountResponse> getSellersWithTotalAmountLessThan(BigDecimal amount, LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> results = transactionRepository.findSellersWithTotalAmountLessThan(amount, startDate, endDate);
        List<SellerWithTotalAmountResponse> responses = new ArrayList<>();

        for (Object[] result : results) {
            Seller seller = (Seller) result[0];
            BigDecimal totalAmount = (BigDecimal) result[1];

            SellerWithTotalAmountResponse response = new SellerWithTotalAmountResponse();
            response.setId(seller.getId());
            response.setName(seller.getName());
            response.setContactInfo(seller.getContactInfo());
            response.setRegistrationDate(seller.getRegistrationDate().toString());
            response.setTotalAmount(totalAmount);

            responses.add(response);
        }

        return responses;
    }

    public BestPeriodResponse getBestPeriodForSeller(Long sellerId) {
    List<Transaction> transactions = transactionRepository.findBySellerIdOrderByTransactionDateAsc(sellerId);

    if (transactions.isEmpty()) {
        return null;
    }

    int maxCount = 0;
    LocalDateTime bestStart = null;
    LocalDateTime bestEnd = null;

    // Алгоритм имеет сложность O(n^2) но можно написать и на O(n) 
    //но уже с ограниченной максимальной длительностью периода

    int n = transactions.size();
    for (int i = 0; i < n; i++) {
        for (int j = i; j < n; j++) {
            int count = j - i + 1;
            if (count >= maxCount) {
                LocalDateTime start = transactions.get(i).getTransactionDate();
                LocalDateTime end = transactions.get(j).getTransactionDate();
                if (count > maxCount || (count == maxCount && (bestEnd == null || end.isBefore(bestEnd)))) {
                    maxCount = count;
                    bestStart = start;
                    bestEnd = end;
                }
            }
        }
    }

    BestPeriodResponse response = new BestPeriodResponse();
    response.setSellerId(sellerId);
    response.setTransactionCount(maxCount);
    response.setStartDate(bestStart);
    response.setEndDate(bestEnd);

    return response;
    }
}
