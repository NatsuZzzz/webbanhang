package com.example.asm1.service;

import com.example.asm1.Entity.Product;
import com.example.asm1.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ✅ Lấy 1 sản phẩm theo ID (cho trang chi tiết)
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    /**
     * Lấy toàn bộ sản phẩm
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }



    /**
     * Lọc theo danh mục
     */
    public List<Product> getProductsByCategory(Integer categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    /**
     * Lọc theo khoảng giá
     */
    public List<Product> getProductsByPrice(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    /**
     * Lọc theo danh mục + giá (QUAN TRỌNG NHẤT)
     */
    public List<Product> filterProducts(Integer categoryId,
                                        Double minPrice,
                                        Double maxPrice) {

        // Không chọn gì → trả hết
        if (categoryId == null && minPrice == null && maxPrice == null) {
            return productRepository.findAll();
        }

        // Chỉ danh mục
        if (categoryId != null && minPrice == null && maxPrice == null) {
            return productRepository.findByCategoryId(categoryId);
        }

        // Chỉ giá
        if (categoryId == null && minPrice != null && maxPrice != null) {
            return productRepository.findByPriceBetween(minPrice, maxPrice);
        }

        // CẢ HAI
        return productRepository
                .findByCategoryIdAndPriceBetween(categoryId, minPrice, maxPrice);
    }
}
