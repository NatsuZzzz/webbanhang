package com.example.asm1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendOtp(String toEmail) {
        // Tạo mã ngẫu nhiên 6 số
        String otp = String.valueOf(new Random().nextInt(899999) + 100000);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("phamkhoi2746@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Mã xác nhận đăng ký Nike Member");
        message.setText("Chào ku em, mã xác nhận của em là: " + otp);

        mailSender.send(message);
        return otp; // Trả về mã để lưu vào session
    }
}