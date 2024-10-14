package com.example.crm_project.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sellers")
@Data
@NoArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String contactInfo;

    private LocalDateTime registrationDate = LocalDateTime.now();

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Transaction> transactions;
}
