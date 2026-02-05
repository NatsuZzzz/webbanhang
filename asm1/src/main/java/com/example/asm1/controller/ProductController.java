package com.example.asm1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.asm1.Entity.Category;
import com.example.asm1.Entity.Product;
import com.example.asm1.service.CategoryService;
import com.example.asm1.service.ProductService;


@Controller
@RequestMapping("/list")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // ✅ Trang danh sách sản phẩm
    @GetMapping("/products")
    public String showProducts(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            Model model) {

        List<Product> products = productService
                .filterProducts(categoryId, minPrice, maxPrice);
        List<Category> categories = categoryService.findAll();

        model.addAttribute("categories", categories);
        System.out.println("👉 Products size = " + products.size());
        model.addAttribute("products", products);
        model.addAttribute("totalItems", products.size());

        // giữ lại giá trị lọc để frontend check lại checkbox
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        return "Product";
    }
    // 1. Hiển thị form đăng bài
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        // Lấy danh sách category để đổ vào dropdown (select box)
        model.addAttribute("categories", categoryService.findAll()); 
        return "AddProduct"; // Tên file HTML bạn sẽ tạo ở bước sau
    }

    // 2. Xử lý lưu bài đăng
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product); // Đảm bảo ProductService đã có hàm save
        return "redirect:/list/products"; // Lưu xong quay về danh sách
    }

    
    

    // ✅ Trang chi tiết sản phẩm
    @GetMapping("/product/detail/{id}")
    public String showProductDetail(@PathVariable Integer id, Model model) {

        Product product = productService.getProductById(id);

        if (product == null) {
            return "redirect:/list/products";
        }

        model.addAttribute("product", product);
        return "ProductDetail";
    }
    

}
