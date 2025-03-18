package org.example.shoppingmall.service.login;

import org.example.shoppingmall.dto.User.UserInfoDto;
import org.example.shoppingmall.repository.User.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //서비스 단
    public String getCustomerId(String customerId) {
        String testData = userRepository.getCustomerId(customerId);
        return testData;
    }
}
