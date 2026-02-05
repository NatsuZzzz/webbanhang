
package com.example.asm1.controller;

import com.example.asm1.Entity.User;
import com.example.asm1.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // 1. Hiển thị trang đăng ký
    @GetMapping("/register")
    public String showRegisterForm(Model model, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        // Nếu chưa qua bước check email thì quay lại
        if (email == null) return "redirect:/checkMail";

        User user = new User();
        user.setEmail(email);
        model.addAttribute("registerForm", user); 
        return "register"; 
    }

    // 2. Xử lý đăng ký
  @PostMapping("/register")
public String processRegister(
        @Valid @ModelAttribute("registerForm") User user,
        BindingResult bindingResult,
        HttpSession session,
        Model model) {

    // 1. Kiểm tra lỗi Validation (trống trường, mật khẩu...)
    if (bindingResult.hasErrors()) {
        return "register";
    }

    // 2. Kiểm tra mã OTP
    String sessionOtp = (String) session.getAttribute("otpCode");
    if (sessionOtp == null || !sessionOtp.equals(user.getCode())) {
        bindingResult.rejectValue("code", "error.user", "Mã xác nhận không đúng!");
        return "register";
    }

    try {
        // Kiểm tra trùng email
        if (userRepository.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email đã tồn tại!");
            return "register";
        }

        // Gán Role mặc định từ DB (USER có ID là 2 dựa trên SQL của bạn)
        user.setRoleId(2); 
        userRepository.save(user);

        session.removeAttribute("otpCode");
        session.removeAttribute("userEmail");

        return "redirect:/login"; // TRẢ VỀ TRANG ĐĂNG NHẬP KHI THÀNH CÔNG

    } catch (Exception e) {
        e.printStackTrace();
        model.addAttribute("error", "Lỗi DB: Hãy đảm bảo đã chạy SQL tạo bảng mới!");
        return "register";
    }
}
    // 3. Đăng nhập
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        User user = userRepository.findByEmail(email.trim());

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/home";
        }

        model.addAttribute("error", "Email hoặc mật khẩu không chính xác!");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }
}