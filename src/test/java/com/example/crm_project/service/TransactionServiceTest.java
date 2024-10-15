package com.example.crm_project.service;

import com.example.crm_project.exception.TransactionNotFoundException;
import com.example.crm_project.model.PaymentType;
import com.example.crm_project.model.Transaction;
import com.example.crm_project.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void testFindAll() {
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setAmount(new BigDecimal("1000"));
        transaction1.setPaymentType(PaymentType.CARD);

        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setAmount(new BigDecimal("2000"));
        transaction2.setPaymentType(PaymentType.CASH);

        when(transactionRepository.findAll()).thenReturn(Arrays.asList(transaction1, transaction2));

        List<Transaction> result = transactionService.findAll();

        assertEquals(2, result.size());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Success() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(new BigDecimal("1000"));

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        Transaction foundTransaction = transactionService.findById(1L);

        assertNotNull(foundTransaction);
        assertEquals(new BigDecimal("1000"), foundTransaction.getAmount());
    }

    @Test
    void testFindById_NotFound() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () -> transactionService.findById(1L));
    }

    @Test
    void testSave() {
        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal("1500"));
        transaction.setPaymentType(PaymentType.TRANSFER);

        Transaction savedTransaction = new Transaction();
        savedTransaction.setId(3L);
        savedTransaction.setAmount(new BigDecimal("1500"));
        savedTransaction.setPaymentType(PaymentType.TRANSFER);

        when(transactionRepository.save(transaction)).thenReturn(savedTransaction);

        Transaction result = transactionService.save(transaction);

        assertNotNull(result.getId());
        assertEquals(new BigDecimal("1500"), result.getAmount());
    }

    @Test
    void testDeleteById_Success() {
        when(transactionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(transactionRepository).deleteById(1L);

        transactionService.deleteById(1L);

        verify(transactionRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_NotFound() {
        when(transactionRepository.existsById(1L)).thenReturn(false);

        assertThrows(TransactionNotFoundException.class, () -> transactionService.deleteById(1L));
    }
}
