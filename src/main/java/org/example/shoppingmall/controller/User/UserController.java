package org.example.shoppingmall.controller.User;

import org.example.shoppingmall.dto.User.UserInfoDto;
import org.example.shoppingmall.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class UserController {

    private final TestService testService;


    @Autowired
    public UserController(TestService testService)
    {
        this.testService = testService;
    }
    @GetMapping("/test") // /test라는 url을 쳤을때 렌더링 되는 api
    public List<UserInfoDto> getTestData()
    {
        return testService.getTestData();
    }

    @GetMapping("/user/login") // /test라는 url을 쳤을때 렌더링 되는 api
    public String getCheck()
    {
        return "user/login";
    }

    @GetMapping("/user/register") // /test라는 url을 쳤을때 렌더링 되는 api
    public String getRegister()
    {
        return "user/register";
    }

    @GetMapping("/user/mypage") // /test라는 url을 쳤을때 렌더링 되는 api
    public String getMypage()
    {
        return "user/mypage";
    }
}
