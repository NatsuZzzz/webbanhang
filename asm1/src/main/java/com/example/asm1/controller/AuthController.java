package com.example.asm1.controller;

import com.example.asm1.Entity.RegisterForm;
import jakarta.servlet.http.HttpSession;
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
    public String showRegisterForm(Model model, HttpSession session) {

        // --- LẤY EMAIL TỪ SESSION ---
        String receivedEmail = (String) session.getAttribute("userEmail");

        // [CHỐT CHẶN] Nếu chưa có email -> ĐÁ VỀ
        if (receivedEmail == null || receivedEmail.isEmpty()) {

            // --- CÁCH MỚI: LƯU LỖI VÀO SESSION (Chắc chắn nhận được) ---
            session.setAttribute("sessionError", "Please enter your email first!");

            System.out.println("Không có email -> Đá về /checkMail");
            return "redirect:/checkMail";
        }

        // Nếu có email -> Điền vào form
        RegisterForm form = new RegisterForm();
        form.setEmail(receivedEmail);

        model.addAttribute("registerForm", form);
        return "register";
    }

    // NEWLINE
    // 2. POST: Xử lý đăng ký
    // 2. POST: Xử lý đăng ký (Có kiểm tra mã OTP)
    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("registerForm") RegisterForm registerForm,
            BindingResult bindingResult,
            HttpSession session,
            Model model) {

        // 1. Kiểm tra lỗi Validation từ Form (Trống, định dạng email, password...)
        if (bindingResult.hasErrors()) {
            return "register";
        }

        // 2. Lấy mã OTP từ session và từ người dùng nhập
        String correctOtp = (String) session.getAttribute("otpCode");
        String userEnteredCode = registerForm.getCode();

        // 3. LOGIC KIỂM TRA (Thay thế cho cái everythingIsOk bị lỗi)
        if (correctOtp == null) {
            bindingResult.rejectValue("code", "error.registerForm", "Mã đã hết hạn, vui lòng gửi lại mã!");
            return "register";
        }

        if (!correctOtp.equals(userEnteredCode)) {
            bindingResult.rejectValue("code", "error.registerForm", "Mã xác nhận không đúng!");
            return "register";
        }

        // --- ĐẾN ĐÂY LÀ "EVERYTHING IS OK" RỒI ĐẤY ---

        // 4. Lưu thông tin người dùng vào Session để Header hiển thị tên
        session.setAttribute("loggedInUser", registerForm);

        // 5. Xóa mã OTP sau khi dùng xong
        session.removeAttribute("otpCode");

        System.out.println("Đăng ký thành công cho Member: " + registerForm.getFirstName());
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 1. Xóa toàn bộ dữ liệu trong session để người dùng thoát ra hoàn toàn
        session.invalidate();

        // 2. Sau khi thoát, đá người dùng về trang chủ (lúc này Header sẽ tự hiện lại
        // nút Sign In)
        return "redirect:/home";
    }
}