package com.example.asm1.Entity;

import com.example.asm1.Entity.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Double price;

    @Column(name = "image_url")
    private String imageUrl;

    private String description;

    @Column(name = "is_available")
    private Boolean isAvailable;

    // ===== QUAN HỆ CATEGORY =====
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // getter / setter
}
