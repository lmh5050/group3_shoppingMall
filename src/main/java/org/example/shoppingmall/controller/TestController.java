package org.example.shoppingmall.controller;

import org.example.shoppingmall.dto.UserInfoDto;
import org.example.shoppingmall.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    private final TestService testService;


    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }
    @GetMapping("/test") // /test라는 url을 쳤을때 렌더링 되는 api
    public List<UserInfoDto> getTestData() {
        return testService.getTestData();
    }
}
