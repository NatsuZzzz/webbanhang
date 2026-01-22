package com.example.asm1.controller;

import com.example.asm1.Entity.RegisterForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    // 1. GET: Hiển thị trang đăng ký
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        
        // --- [CHỐT CHẶN] KIỂM TRA EMAIL ---
        
        // Kiểm tra xem có email được gửi sang từ trang CheckMail không
        if (!model.containsAttribute("userEmail")) {
            // Nếu không có email (truy cập chui hoặc chưa nhập) -> ĐÁ VỀ TRANG NHẬP MAIL
            return "redirect:/checkMail"; 
        }

        // --- [NHẬN BÓNG] LẤY EMAIL RA ---
        String receivedEmail = (String) model.getAttribute("userEmail");

        // Tạo form mới và ĐIỀN SẴN EMAIL vào
        RegisterForm form = new RegisterForm();
        form.setEmail(receivedEmail); // <--- Dòng này giúp email hiện lên form

        model.addAttribute("registerForm", form);
        return "register"; 
    }

    // 2. POST: Xử lý đăng ký (Giữ nguyên logic của em)
    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("registerForm") RegisterForm registerForm,
                                  BindingResult bindingResult,
                                  Model model) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        // Test xem email có được gửi xuống không
        System.out.println("Đăng ký thành công!");
        System.out.println("Email: " + registerForm.getEmail()); 

        return "redirect:/home";
    }
}