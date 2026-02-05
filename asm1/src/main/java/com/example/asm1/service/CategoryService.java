package com.example.asm1.service;

import com.example.asm1.Entity.Category;
import com.example.asm1.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Lấy toàn bộ danh mục
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
