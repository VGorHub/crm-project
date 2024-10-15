package com.example.crm_project.controller;

import com.example.crm_project.dto.CreateSellerRequest;
import com.example.crm_project.model.Seller;
import com.example.crm_project.service.SellerService;
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

import java.util.Arrays;

@WebMvcTest(SellerController.class)
class SellerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SellerService sellerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllSellers() throws Exception {
        Seller seller1 = new Seller();
        seller1.setId(1L);
        seller1.setName("Иван Иванов");

        Seller seller2 = new Seller();
        seller2.setId(2L);
        seller2.setName("Петр Петров");

        when(sellerService.findAll()).thenReturn(Arrays.asList(seller1, seller2));

        mockMvc.perform(get("/sellers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetSellerById() throws Exception {
        Seller seller = new Seller();
        seller.setId(1L);
        seller.setName("Иван Иванов");

        when(sellerService.findById(1L)).thenReturn(seller);

        mockMvc.perform(get("/sellers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Иван Иванов"));
    }

    @Test
    void testCreateSeller() throws Exception {
        CreateSellerRequest request = new CreateSellerRequest();
        request.setName("Новый Продавец");
        request.setContactInfo("+7 (555) 123-45-67");

        Seller savedSeller = new Seller();
        savedSeller.setId(3L);
        savedSeller.setName("Новый Продавец");
        savedSeller.setContactInfo("+7 (555) 123-45-67");

        when(sellerService.save(any(Seller.class))).thenReturn(savedSeller);

        mockMvc.perform(post("/sellers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("Новый Продавец"));
    }

    @Test
    void testUpdateSeller() throws Exception {
        Seller existingSeller = new Seller();
        existingSeller.setId(1L);
        existingSeller.setName("Старый Продавец");

        Seller updatedSeller = new Seller();
        updatedSeller.setId(1L);
        updatedSeller.setName("Обновленный Продавец");

        when(sellerService.findById(1L)).thenReturn(existingSeller);
        when(sellerService.save(any(Seller.class))).thenReturn(updatedSeller);

        mockMvc.perform(put("/sellers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Обновленный Продавец\",\"contactInfo\":\"+7 (555) 123-45-67\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Обновленный Продавец"));
    }

    @Test
    void testDeleteSeller() throws Exception {
        doNothing().when(sellerService).deleteById(1L);

        mockMvc.perform(delete("/sellers/1"))
                .andExpect(status().isNoContent());
    }
}
