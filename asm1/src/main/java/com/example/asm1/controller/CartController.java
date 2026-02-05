package com.example.asm1.controller;

import com.example.asm1.Entity.Product;
import com.example.asm1.model.CartItem;
import com.example.asm1.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductService productService;

    public CartController(ProductService productService) {
        this.productService = productService;
    }

    // 🛒 XEM GIỎ HÀNG
    @GetMapping
    public String viewCart(HttpSession session, Model model) {

        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
        }

        model.addAttribute("cart", cart.values());

        double total = cart.values()
                .stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();

        model.addAttribute("totalPrice", total);

        return "Cart";
    }

    // ➕ THÊM VÀO GIỎ
    @PostMapping("/add/{id}")
    public String addToCart(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "1") int quantity,
            HttpSession session) {

        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/list/products";
        }

        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
        }

        if (cart.containsKey(id)) {
            CartItem item = cart.get(id);
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            cart.put(id, new CartItem(product, quantity));
        }

        session.setAttribute("cart", cart);

        return "redirect:/cart";
    }

    // ❌ XÓA SẢN PHẨM
    @GetMapping("/remove/{id}")
    public String remove(@PathVariable Integer id, HttpSession session) {

        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");

        if (cart != null) {
            cart.remove(id);
        }

        return "redirect:/cart";
    }
}
