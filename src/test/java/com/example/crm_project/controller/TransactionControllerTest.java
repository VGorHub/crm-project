package com.example.crm_project.controller;

import com.example.crm_project.dto.CreateTransactionRequest;
import com.example.crm_project.model.PaymentType;
import com.example.crm_project.model.Seller;
import com.example.crm_project.model.Transaction;
import com.example.crm_project.service.SellerService;
import com.example.crm_project.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private SellerService sellerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllTransactions() throws Exception {
        Seller seller = new Seller();
        seller.setId(1L);

        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setAmount(new BigDecimal("1000"));
        transaction1.setSeller(seller);
        transaction1.setPaymentType(PaymentType.CARD);
        transaction1.setTransactionDate(LocalDateTime.now());

        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setAmount(new BigDecimal("2000"));
        transaction2.setSeller(seller);
        transaction2.setPaymentType(PaymentType.CASH);
        transaction2.setTransactionDate(LocalDateTime.now());

        when(transactionService.findAll()).thenReturn(Arrays.asList(transaction1, transaction2));

        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }



    @Test
    void testGetTransactionById() throws Exception {
        Seller seller = new Seller();
        seller.setId(1L);

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(new BigDecimal("1000"));
        transaction.setSeller(seller);
        transaction.setPaymentType(PaymentType.CARD);
        transaction.setTransactionDate(LocalDateTime.now());

        when(transactionService.findById(1L)).thenReturn(transaction);

        mockMvc.perform(get("/transactions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(1000));
    }


    @Test
    void testCreateTransaction() throws Exception {
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setSellerId(1L);
        request.setAmount(new BigDecimal("1500"));
        request.setPaymentType("CARD");

        Seller seller = new Seller();
        seller.setId(1L);

        Transaction savedTransaction = new Transaction();
        savedTransaction.setId(3L);
        savedTransaction.setAmount(new BigDecimal("1500"));
        savedTransaction.setPaymentType(PaymentType.CARD);
        savedTransaction.setSeller(seller);

        when(sellerService.findById(1L)).thenReturn(seller);
        when(transactionService.save(any(Transaction.class))).thenReturn(savedTransaction);

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.amount").value(1500));
    }

    @Test
    void testGetTransactionsBySeller() throws Exception {
        Seller seller = new Seller();
        seller.setId(1L);

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(new BigDecimal("1000"));
        transaction.setSeller(seller);
        transaction.setPaymentType(PaymentType.CARD);
        transaction.setTransactionDate(LocalDateTime.now());

        when(transactionService.findBySellerId(1L)).thenReturn(Arrays.asList(transaction));

        mockMvc.perform(get("/transactions/seller/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

}
