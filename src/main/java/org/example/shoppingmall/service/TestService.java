package org.example.shoppingmall.service;

import org.example.shoppingmall.dto.User.UserInfoDto;
import org.example.shoppingmall.repository.User.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    private final UserRepository userRepository;

    public TestService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //서비스 단
    public List<UserInfoDto> getTestData() {
        return userRepository.getTestData();
    }
}
