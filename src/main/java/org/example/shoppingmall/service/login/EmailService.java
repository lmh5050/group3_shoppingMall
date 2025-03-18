package org.example.shoppingmall.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public String sendVerificationEmail(String toEmail) {
        String verificationCode = UUID.randomUUID().toString();  // 고유 인증 코드 생성
        String verificationUrl = "http://localhost:8080/verify?code=" + verificationCode;

        SimpleMailMessage message = new SimpleMailMessage();
        System.out.println(toEmail);

        // 이메일 주소가 올바른지 확인
        if (toEmail != null && !toEmail.trim().isEmpty()) {
            message.setTo(toEmail.trim());  // 공백을 제거한 이메일 주소 사용
            message.setSubject("이메일 인증");
            message.setText("다음 링크를 클릭하여 이메일 인증을 완료해주세요:\n\n" + verificationUrl);

            mailSender.send(message);

            // 인증 코드와 함께 이메일 주소를 DB에 저장하는 로직 필요
            // 예시: userService.saveVerificationCode(toEmail, verificationCode);
        } else {
            throw new IllegalArgumentException("유효하지 않은 이메일 주소입니다.");
        }
        return "123";
    }
}