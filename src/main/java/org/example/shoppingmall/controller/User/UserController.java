package org.example.shoppingmall.controller.User;

import jakarta.servlet.http.HttpServletResponse;
import org.example.shoppingmall.dto.User.InsertUserInfoDto;
import org.example.shoppingmall.dto.User.UserEmailDto;
import org.example.shoppingmall.dto.User.UserInfoDto;
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

    @GetMapping("/user/login") // 로그인 페이지 불러오는 api
    public String getCheck()
    {
        return "user/login";
    }


    @PostMapping("/user/login") //유저 로그인 실제적 기능하는 api
    @ResponseBody
    public UserLoginInfoDto checkLoginInfo(@RequestBody UserLoginInfoDto loginInfo, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        UserLoginInfoDto result = loginService.userLogin(loginInfo);
        return result;
    }

    @GetMapping("/user/mypage") // mypage 들어가는 api
    public String getMypage()
    {
        return "user/mypage";
    }

    @GetMapping("/user/register") //회원가입 페이지 렌더링 하는 api
    public String getRegister()
    {
        return "user/register";
    }

    @PostMapping("/user/registerCheck") //아이디 중복 체크 입니다.
    @ResponseBody
    public String registerCheck(@RequestBody UserLoginInfoDto loginInfo, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
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

    @PostMapping("/user/checkNickname") //닉네임 중복 확인하는 api
    @ResponseBody
    public String checkNickname(@RequestBody UserInfoDto userInfo) {
        String nickName = userInfo.getNickname();
        String result = loginService.checkNickname(nickName);
        return result;
    }

    @PostMapping("/user/register") //회원가입 할때 타는 api
    @ResponseBody
    public String insertUserInfo(@RequestBody InsertUserInfoDto InsertUserInfo) {
        String result = loginService.insertUserInfo(InsertUserInfo);
        return result;
    }

    @PostMapping("/user/emailSend") //이메일 인증 , 디벨롭 필요 api
    @ResponseBody //axios json 이용하기 위해서 responseBody 추가
    public String emailSend(@RequestBody UserEmailDto email, HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 아이디 확인
        emailService.sendVerificationEmail(email.getEmail());
        return "123"; // 결과 반환
    }

}
