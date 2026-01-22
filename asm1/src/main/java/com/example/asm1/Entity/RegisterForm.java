package com.example.asm1.Entity;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterForm {

    // --- QUAN TRỌNG: Phải có field này để hứng email từ trang trước gửi sang ---
    private String email; 

    // --- Các field khác của trang Register ---

    @NotBlank(message = "Required")
    private String code;

    @NotBlank(message = "Required")
    private String firstName;

    @NotBlank(message = "Required")
    private String surname;

    @NotBlank(message = "Required")
    @Size(min = 8, message = "Minimum of 8 characters")
    // Regex này bắt buộc: 1 chữ thường, 1 chữ hoa, 1 số (giống yêu cầu Nike)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "Uppercase, lowercase letters and one number")
    private String password;

    @NotBlank(message = "Please select a preference")
    private String shoppingPreference;

    // Ngày tháng năm sinh (để String cho dễ nhập, xử lý sau)
    @NotBlank(message = "Required")
    private String dobDay;

    @NotBlank(message = "Required")
    private String dobMonth;

    @NotBlank(message = "Required")
    private String dobYear;

    private boolean emailSignup; // Checkbox nhận tin

    @AssertTrue(message = "You must agree to the terms") // Bắt buộc phải tích
    private boolean agreeTerms;
}