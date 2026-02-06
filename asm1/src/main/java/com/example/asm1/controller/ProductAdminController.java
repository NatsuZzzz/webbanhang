package com.example.asm1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.asm1.Entity.Product;
import com.example.asm1.repository.ProductRepository;
import com.example.asm1.controller.AuthUtil;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/products")
public class ProductAdminController {

    @Autowired
    ProductRepository productRepo;

    // ====== TRANG QUẢN LÝ SẢN PHẨM ======
    @GetMapping
    public String index(HttpSession session, Model model) {
        if (!AuthUtil.isAdmin(session)) {
            return "redirect:/403";
        }

        model.addAttribute("products", productRepo.findAll());
        return "admin/products";
    }

    // ====== THÊM SẢN PHẨM ======
    @PostMapping("/create")
    public String create(Product product, HttpSession session) {
        if (!AuthUtil.isAdmin(session)) {
            return "redirect:/403";
        }

        productRepo.save(product);
        return "redirect:/admin/products";
    }

    // ====== XÓA SẢN PHẨM ======
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, HttpSession session) {
        if (!AuthUtil.isAdmin(session)) {
            return "redirect:/403";
        }

        productRepo.deleteById(id);
        return "redirect:/admin/products";
    }
}
