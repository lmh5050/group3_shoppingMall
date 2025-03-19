package org.example.shoppingmall.service.login;

import org.example.shoppingmall.dto.User.InsertUserInfoDto;
import org.example.shoppingmall.dto.User.UserInfoDto;
import org.example.shoppingmall.repository.User.UserRepository;
import org.example.shoppingmall.service.login.PasswordUtillService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordUtillService passwordUtillService;

    public LoginService(UserRepository userRepository , PasswordUtillService passwordUtillService) {
        this.userRepository = userRepository;
        this.passwordUtillService = passwordUtillService;
    }

    //서비스 단
    public String getCustomerId(String customerId) {
        String testData = userRepository.getCustomerId(customerId);
        return testData;
    }

    public String insertUserInfo(InsertUserInfoDto InsertUserInfo) {
        try {
            String encryptedPassword = passwordUtillService.encryptPassword(InsertUserInfo.getPw());
            InsertUserInfo.setPw(encryptedPassword);  // 암호화된 비밀번호와 Salt를 설정
            System.out.println(InsertUserInfo.getNickName() + "닉네임 이름이름");
            userRepository.insertUserInfo(InsertUserInfo);
            return "success";

        } catch (NoSuchAlgorithmException e) {
            // 예외 처리
            e.printStackTrace();
        }
        return "";
    }
}
