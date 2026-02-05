package com.example.asm1.repository;

import com.example.asm1.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategoryId(Integer categoryId);

    List<Product> findByPriceBetween(Double min, Double max);

    List<Product> findByCategoryIdAndPriceBetween(
            Integer categoryId,
            Double min,
            Double max);

}
