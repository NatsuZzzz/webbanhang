package com.example.asm1.controller;

import com.example.asm1.Entity.MailForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // <--- Nhớ import cái này

@Controller
public class CheckMailController {

    // 1. GET: Hiển thị form nhập email
    @GetMapping("/checkMail")
    public String showLoginPage(Model model) {
        // Đặt tên gói hàng là "ChangeMail" (HTML phải dùng th:object="${ChangeMail}")
        model.addAttribute("ChangeMail", new MailForm());
        return "ChangeMail"; 
    }

    // 2. POST: Xử lý khi bấm nút Continue
    @PostMapping("/login/check-email")
    public String checkEmail(@Valid @ModelAttribute("ChangeMail") MailForm mailForm,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) { // <--- Thêm tham số này để gửi dữ liệu đi
        
        // Nếu validation lỗi (trống, sai định dạng) -> Quay lại trang cũ hiện lỗi
        if (bindingResult.hasErrors()) {
            return "ChangeMail"; 
        }

        // --- LOGIC: GỬI EMAIL SANG TRANG REGISTER ---
        System.out.println("Email nhận được: " + mailForm.getEmail());

        // Dùng FlashAttribute để gửi email đi (nó sẽ tự xóa sau khi dùng xong, rất an toàn)
        redirectAttributes.addFlashAttribute("userEmail", mailForm.getEmail());

        // Chuyển hướng sang trang đăng ký
        return "redirect:/register"; 
    }
}