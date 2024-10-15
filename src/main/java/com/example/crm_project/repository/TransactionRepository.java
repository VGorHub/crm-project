package com.example.crm_project.repository;

import com.example.crm_project.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySellerId(Long sellerId);

    @Query("SELECT t.seller AS seller, SUM(t.amount) AS totalAmount FROM Transaction t " +
            "WHERE t.transactionDate BETWEEN :startDate AND :endDate " +
            "GROUP BY t.seller " +
            "ORDER BY totalAmount DESC")
    List<Object[]> findTotalAmountBySellerInPeriod(@Param("startDate") LocalDateTime startDate,
                                                   @Param("endDate") LocalDateTime endDate);


    @Query("SELECT t.seller AS seller, SUM(t.amount) AS totalAmount FROM Transaction t " +
            "WHERE t.transactionDate BETWEEN :startDate AND :endDate " +
            "GROUP BY t.seller " +
            "HAVING SUM(t.amount) < :amount")
    List<Object[]> findSellersWithTotalAmountLessThan(@Param("amount") BigDecimal amount,
                                                      @Param("startDate") LocalDateTime startDate,
                                                      @Param("endDate") LocalDateTime endDate);

    List<Transaction> findBySellerIdOrderByTransactionDateAsc(Long sellerId);
}
