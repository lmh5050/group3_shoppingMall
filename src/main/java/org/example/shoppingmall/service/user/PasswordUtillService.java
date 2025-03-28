package org.example.shoppingmall.service.user;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class PasswordUtillService {
    // UUID를 Salt로 사용하여 비밀번호 암호화
    public static String encryptPassword(String password) throws NoSuchAlgorithmException {
        // UUID를 salt로 생성
        String salt = UUID.randomUUID().toString();

        // 비밀번호와 Salt를 결합
        String saltedPassword = salt + password;

        // 비밀번호 해시화 (SHA-256 사용 예시)
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(saltedPassword.getBytes());

        // 해시값을 Hex 형태로 반환
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }

        // 암호화된 비밀번호와 Salt 반환 (저장용)
        return salt + ":" + hexString.toString();  // "salt:hashedPassword"
    }

    public static boolean verifyPassword(String enteredPassword, String storedPassword) throws NoSuchAlgorithmException {
        if (storedPassword == null || !storedPassword.contains(":")) {
            throw new IllegalArgumentException("잘못된 저장된 비밀번호 형식입니다.");
        }

        // 저장된 비밀번호에서 Salt와 해시된 비밀번호 분리
        String[] parts = storedPassword.split(":");

        if (parts.length != 2) {
            throw new IllegalArgumentException("비밀번호 데이터가 올바르지 않습니다.");
        }

        String storedSalt = parts[0];
        String storedHashedPassword = parts[1];

        // 입력된 비밀번호를 같은 방식으로 해시화
        String saltedPassword = storedSalt + enteredPassword;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(saltedPassword.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }

        // 해시값 비교
        return hexString.toString().equals(storedHashedPassword);
    }
}
