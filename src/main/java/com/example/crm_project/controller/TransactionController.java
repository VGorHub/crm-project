package com.example.crm_project.controller;

import com.example.crm_project.dto.CreateTransactionRequest;
import com.example.crm_project.dto.TransactionResponse;
import com.example.crm_project.model.PaymentType;
import com.example.crm_project.model.Seller;
import com.example.crm_project.model.Transaction;
import com.example.crm_project.service.SellerService;
import com.example.crm_project.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Tag(name = "Транзакции", description = "API для управления транзакциями")
public class TransactionController {

    private final TransactionService transactionService;
    private final SellerService sellerService;

    @Operation(summary = "Получить список всех транзакций")
    @GetMapping
    public List<TransactionResponse> getAllTransactions() {
        return transactionService.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Получить информацию о конкретной транзакции")
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long id) {
        return transactionService.findById(id)
                .map(transaction -> ResponseEntity.ok(convertToResponse(transaction)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создать новую транзакцию")
    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody CreateTransactionRequest request) {
        Seller seller = sellerService.findById(request.getSellerId())
                .orElseThrow(() -> new RuntimeException("Продавец не найден"));
        Transaction transaction = new Transaction();
        transaction.setSeller(seller);
        transaction.setAmount(request.getAmount());
        transaction.setPaymentType(PaymentType.valueOf(request.getPaymentType()));
        Transaction savedTransaction = transactionService.save(transaction);
        return ResponseEntity.ok(convertToResponse(savedTransaction));
    }

    @Operation(summary = "Получить все транзакции продавца")
    @GetMapping("/seller/{sellerId}")
    public List<TransactionResponse> getTransactionsBySeller(@PathVariable Long sellerId) {
        return transactionService.findBySellerId(sellerId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private TransactionResponse convertToResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setSellerId(transaction.getSeller().getId());
        response.setAmount(transaction.getAmount());
        response.setPaymentType(transaction.getPaymentType().name());
        response.setTransactionDate(transaction.getTransactionDate().toString());
        return response;
    }
}
