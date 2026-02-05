package com.example.asm1.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "Categories")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    // 1 Category có nhiều Food
    @OneToMany(mappedBy = "category")
    private List<Product> foods;
}
