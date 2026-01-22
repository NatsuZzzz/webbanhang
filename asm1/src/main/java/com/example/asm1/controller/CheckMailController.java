package com.example.asm1.controller;

import com.example.asm1.Entity.MailForm;
import com.example.asm1.service.MailService; // <--- Import service
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired; // <--- Import
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CheckMailController {

    @Autowired
    private MailService mailService; // <--- Tiêm service vào đây

    @GetMapping("/checkMail")
    public String showLoginPage(Model model, HttpSession session) {
        model.addAttribute("ChangeMail", new MailForm());
        String error = (String) session.getAttribute("sessionError");
        if (error != null) {
            model.addAttribute("errorMessage", error);
            session.removeAttribute("sessionError");
        }
        return "ChangeMail"; 
    }

    @PostMapping("/login/check-email")
    public String checkEmail(@Valid @ModelAttribute("ChangeMail") MailForm mailForm,
                             BindingResult bindingResult, 
                             HttpSession session) { 
        
        System.out.println("--- DEBUG START ---");
        
        if (bindingResult.hasErrors()) {
            return "ChangeMail"; 
        }

        String email = mailForm.getEmail();
        if (email == null || email.trim().isEmpty()) {
            return "ChangeMail"; 
        }

        try {
            // --- ĐOẠN QUAN TRỌNG ĐÂY KU EM ---
            // 1. Gọi service gửi mail thật
            String otpCode = mailService.sendOtp(email);
            
            // 2. Lưu cả Email và Mã OTP vào Session để tí nữa trang Register lôi ra dùng
            session.setAttribute("userEmail", email);
            session.setAttribute("otpCode", otpCode); 
            
            System.out.println("SUCCESS: Đã gửi mã " + otpCode + " tới " + email);
            
            // 3. Sau khi gửi xong thì mới redirect sang Register
            return "redirect:/register"; 

        } catch (Exception e) {
            System.out.println("LỖI GỬI MAIL: " + e.getMessage());
            session.setAttribute("sessionError", "Không gửi được email. Kiểm tra lại kết nối!");
            return "redirect:/checkMail";
        }
    }
}