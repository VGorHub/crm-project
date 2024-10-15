package com.example.crm_project.service;

import com.example.crm_project.exception.SellerNotFoundException;
import com.example.crm_project.model.Seller;
import com.example.crm_project.repository.SellerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class SellerServiceTest {

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private SellerService sellerService;

    @Test
    void testFindAll() {
        Seller seller1 = new Seller();
        seller1.setId(1L);
        seller1.setName("Иван Иванов");
        seller1.setContactInfo("+7 (123) 456-78-90");

        Seller seller2 = new Seller();
        seller2.setId(2L);
        seller2.setName("Петр Петров");
        seller2.setContactInfo("+7 (987) 654-32-10");

        when(sellerRepository.findAll()).thenReturn(Arrays.asList(seller1, seller2));

        List<Seller> result = sellerService.findAll();

        assertEquals(2, result.size());
        verify(sellerRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Success() {
        Seller seller = new Seller();
        seller.setId(1L);
        seller.setName("Иван Иванов");

        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));

        Seller foundSeller = sellerService.findById(1L);

        assertNotNull(foundSeller);
        assertEquals("Иван Иванов", foundSeller.getName());
    }

    @Test
    void testFindById_NotFound() {
        when(sellerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SellerNotFoundException.class, () -> sellerService.findById(1L));
    }

    @Test
    void testSave() {
        Seller seller = new Seller();
        seller.setName("Новый Продавец");
        seller.setContactInfo("+7 (555) 123-45-67");

        Seller savedSeller = new Seller();
        savedSeller.setId(3L);
        savedSeller.setName("Новый Продавец");
        savedSeller.setContactInfo("+7 (555) 123-45-67");

        when(sellerRepository.save(seller)).thenReturn(savedSeller);

        Seller result = sellerService.save(seller);

        assertNotNull(result.getId());
        assertEquals("Новый Продавец", result.getName());
    }

    @Test
    void testDeleteById_Success() {
        when(sellerRepository.existsById(1L)).thenReturn(true);
        doNothing().when(sellerRepository).deleteById(1L);

        sellerService.deleteById(1L);

        verify(sellerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_NotFound() {
        when(sellerRepository.existsById(1L)).thenReturn(false);

        assertThrows(SellerNotFoundException.class, () -> sellerService.deleteById(1L));
    }
}
