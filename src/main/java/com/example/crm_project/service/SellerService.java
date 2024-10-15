package com.example.crm_project.service;

import com.example.crm_project.exception.SellerNotFoundException;
import com.example.crm_project.model.Seller;
import com.example.crm_project.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    public List<Seller> findAll() {
        return sellerRepository.findAll();
    }

    public Seller findById(Long id) {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new SellerNotFoundException("Продавец с id " + id + " не найден"));
    }

    public Seller save(Seller seller) {
        return sellerRepository.save(seller);
    }

    public void deleteById(Long id) {
        if (!sellerRepository.existsById(id)) {
            throw new SellerNotFoundException("Продавец с id " + id + " не найден");
        }
        sellerRepository.deleteById(id);
    }
}