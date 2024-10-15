package com.example.crm_project.controller;

import com.example.crm_project.dto.CreateSellerRequest;
import com.example.crm_project.dto.SellerResponse;
import com.example.crm_project.dto.UpdateSellerRequest;
import com.example.crm_project.model.Seller;
import com.example.crm_project.service.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sellers")
@RequiredArgsConstructor
@Tag(name = "Продавцы", description = "API для управления продавцами")
public class SellerController {

    private final SellerService sellerService;

    @Operation(summary = "Получить список всех продавцов")
    @GetMapping
    public List<SellerResponse> getAllSellers() {
        return sellerService.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerResponse> getSellerById(@PathVariable Long id) {
        Seller seller = sellerService.findById(id);
        SellerResponse response = convertToResponse(seller);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Создать нового продавца")
    @PostMapping
    public ResponseEntity<SellerResponse> createSeller(@Valid @RequestBody CreateSellerRequest request) {
        Seller seller = new Seller();
        seller.setName(request.getName());
        seller.setContactInfo(request.getContactInfo());
        Seller savedSeller = sellerService.save(seller);
        return ResponseEntity.ok(convertToResponse(savedSeller));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SellerResponse> updateSeller(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSellerRequest request) {
        Seller seller = sellerService.findById(id);
        seller.setName(request.getName());
        seller.setContactInfo(request.getContactInfo());
        Seller updatedSeller = sellerService.save(seller);
        SellerResponse response = convertToResponse(updatedSeller);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Удалить продавца")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        sellerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private SellerResponse convertToResponse(Seller seller) {
        SellerResponse response = new SellerResponse();
        response.setId(seller.getId());
        response.setName(seller.getName());
        response.setContactInfo(seller.getContactInfo());
        response.setRegistrationDate(seller.getRegistrationDate().toString());
        return response;
    }
}
