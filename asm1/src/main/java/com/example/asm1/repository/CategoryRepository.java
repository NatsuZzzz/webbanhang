package com.example.asm1.repository;

import com.example.asm1.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Hiện tại chưa cần viết gì thêm
}
