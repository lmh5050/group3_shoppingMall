package org.example.shoppingmall.controller.Shipping;

import org.example.shoppingmall.dto.UserInfoDto;
import org.example.shoppingmall.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ShippingController {

    private final TestService testService;

    @Autowired
    public ShippingController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/tracking") // /tracking URL을 브라우저에서 입력하면 HTML 페이지를 반환
    public String getTestData(Model model) {
        List<UserInfoDto> userInfoList = testService.getTestData();
        model.addAttribute("users", userInfoList);  // 사용자 정보를 HTML로 전달
        return "index";  // test.html 템플릿을 렌더링
    }
}

