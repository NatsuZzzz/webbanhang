package com.example.asm1.controller;

import com.example.asm1.Entity.Product;
import com.example.asm1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // ✅ Trang danh sách sản phẩm
    @GetMapping("/new-arrivals")
    public String showNewArrivalsPage(Model model) {

        List<Product> listProducts = productRepository.findAll();

        model.addAttribute("products", listProducts);
        model.addAttribute("totalItems", listProducts.size());

        return "NewArrival";
    }

    // ✅ Trang chi tiết sản phẩm
    @GetMapping("/product/detail/{id}")
    public String showProductDetail(@PathVariable("id") Integer id, Model model) {

        // 1. Tìm sản phẩm theo ID
        Product product = productRepository.findById(id).orElse(null);

        // 2. Nếu không tìm thấy → quay về trang list
        if (product == null) {
            return "redirect:/new-arrivals";
        }

        // 3. Gửi sản phẩm sang HTML
        model.addAttribute("product", product);

        // 4. Trả về trang chi tiết
        return "ProductDetail";
    }
}
