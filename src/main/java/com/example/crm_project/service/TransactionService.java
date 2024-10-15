package com.example.crm_project.service;

import com.example.crm_project.exception.TransactionNotFoundException;
import com.example.crm_project.model.Transaction;
import com.example.crm_project.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Transaction findById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Транзакция с id " + id + " не найдена"));
    }

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void deleteById(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new TransactionNotFoundException("Транзакция с id " + id + " не найдена");
        }
        transactionRepository.deleteById(id);
    }

    public List<Transaction> findBySellerId(Long sellerId) {
        return transactionRepository.findBySellerId(sellerId);
    }
}
