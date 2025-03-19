package org.example.shoppingmall.controller.User;

import jakarta.servlet.http.HttpServletResponse;
import org.example.shoppingmall.dto.User.InsertUserInfoDto;
import org.example.shoppingmall.dto.User.UserEmailDto;
import org.example.shoppingmall.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.example.shoppingmall.dto.User.UserLoginInfoDto;
import org.springframework.web.bind.annotation.ResponseBody;
import org.example.shoppingmall.service.login.EmailService;

import java.util.Map;

@Controller
public class UserController {

    private final LoginService loginService;

    private final EmailService emailService;
    @Autowired
    public UserController(LoginService loginService, EmailService emailService)
    {
        this.loginService = loginService;
        this.emailService = emailService;
    }

    @GetMapping("/user/login") // /test라는 url을 쳤을때 렌더링 되는 api
    public String getCheck()
    {
        return "user/login";
    }

    @PostMapping("/user/registerCheck") //아이디 중복 체크 입니다.
    @ResponseBody
    public String registerCheck(@RequestBody UserLoginInfoDto loginInfo, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 디버깅용 출력
        System.out.println(loginInfo.getCustomerId() + " 아이디입니다");
        System.out.println(loginInfo.getPw() + " 패스워드입니다.");
        System.out.println("이거탐");

        // 아이디 확인
        String status = loginService.getCustomerId(loginInfo.getCustomerId());
        String resdata = "0"; // 기본값 설정

        if (status != null) {
            // 아이디 일치 여부 확인
            if (status.equals(loginInfo.getCustomerId())) {
                resdata = "일치"; // 일치하면 "일치" 반환
            }
        } else {
            // 아이디가 없으면 "불일치" 반환
            resdata = "불일치";
        }

        return resdata; // 결과 반환
    }

    @PostMapping("/user/emailSend") //아이디 중복 체크 입니다.
    @ResponseBody
    public String emailSend(@RequestBody UserEmailDto email, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 아이디 확인
        System.out.print(email + "이메일 입니다.");
        emailService.sendVerificationEmail(email.getEmail());
        System.out.println("이거탐");


        return "123"; // 결과 반환
    }

    @GetMapping("/user/register") // /test라는 url을 쳤을때 렌더링 되는 api
    public String getRegister()
    {
        return "user/register";
    }

    @PostMapping("/user/login")
    public String checkLoginInfo(@RequestBody UserLoginInfoDto loginInfo, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        System.out.println(loginInfo.getCustomerId() +"아이디입니다");
        System.out.println(loginInfo.getPw() + "패스워드입니다.");
        System.out.println("이거탐");
        return "redirect:/user/login";
    }

    @GetMapping("/user/mypage") // /test라는 url을 쳤을때 렌더링 되는 api
    public String getMypage()
    {
        return "user/mypage";
    }

    @PostMapping("/user/register")
    @ResponseBody
    public String insertUserInfo(@RequestBody InsertUserInfoDto InsertUserInfo) {
        String result = loginService.insertUserInfo(InsertUserInfo);
        System.out.println("이거탐");
        return result;
    }

}
