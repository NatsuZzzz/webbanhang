package com.example.asm1.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    // Dùng SecureRandom thay cho Random để bảo mật hơn cho OTP
    private static final SecureRandom secureRandom = new SecureRandom();

    public String sendOtp(String toEmail) {
        // Tạo mã ngẫu nhiên 6 số
        String otp = String.valueOf(secureRandom.nextInt(899999) + 100000);

        try {
            // Tạo MimeMessage để gửi HTML
            MimeMessage message = mailSender.createMimeMessage();
            // set "true" để hỗ trợ multipart (đính kèm file) và encoding UTF-8
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("phamkhoi2746@gmail.com"); // Tốt nhất nên cấu hình trong application.properties
            helper.setTo(toEmail);
            helper.setSubject("Mã xác nhận đăng ký Nike Member");

            // Nội dung email dạng HTML chuyên nghiệp
            String htmlContent = """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 10px;">
                    <h2 style="color: #111; text-align: center;">Xác thực tài khoản Nike Member</h2>
                    <p>Xin chào,</p>
                    <p>Cảm ơn bạn đã quan tâm đến sản phẩm của chúng tôi. Đây là mã xác thực (OTP) để hoàn tất đăng ký:</p>
                    
                    <div style="background-color: #f4f4f4; padding: 15px; text-align: center; margin: 20px 0; border-radius: 5px;">
                        <span style="font-size: 24px; font-weight: bold; letter-spacing: 5px; color: #fa5400;">%s</span>
                    </div>
                    
                    <p>Mã này có hiệu lực trong vòng <strong>5 phút</strong>. Vui lòng không chia sẻ mã này với bất kỳ ai.</p>
                    
                    <br>
                    <hr style="border: none; border-top: 1px solid #eee;" />
                    
                    <div style="margin-top: 20px; font-size: 13px; color: #666;">
                        <p>Trân trọng,</p>
                        <p><strong>Đội ngũ Hỗ trợ Nike Việt Nam</strong><br>
                        Email: support@nike.com<br>
                        Hotline: 1900 xxxx</p>
                        <p style="font-style: italic; font-size: 11px;">Đây là email tự động, vui lòng không trả lời email này.</p>
                    </div>
                </div>
                """.formatted(otp);

            helper.setText(htmlContent, true); // true = bật chế độ HTML

            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            // Có thể throw lỗi custom hoặc log lỗi tại đây
            return null;
        }

        return otp;
    }
}