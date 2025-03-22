package org.example.shoppingmall.controller.User;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
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


    @PostMapping("/user/login") // 유저 로그인 실제적 기능하는 API
    @ResponseBody
    public UserLoginInfoDto checkLoginInfo(@RequestBody UserLoginInfoDto loginInfo, HttpServletResponse response, HttpSession session) {
        // 응답 콘텐츠 타입 설정
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 로그인 서비스 호출
        UserLoginInfoDto result = loginService.userLogin(loginInfo);

        // 로그인 성공 시 세션에 customerId 저장
        if (result.isStatus()) {
            // 로그인 성공 시 customerId를 세션에 저장
            session.setAttribute("customerId", loginInfo.getCustomerId());
        }

        return result; // 결과 반환
    }

    @GetMapping("/user/mypage")
    public String getMypage(HttpSession session) {
        // 세션에서 customerId 값을 가져옴
        String customerId = (String) session.getAttribute("customerId");
        System.out.println(customerId  + "세션값 가져왔음미다");
        if (customerId != null) {
            // customerId가 세션에 있으면 마이페이지를 렌더링
            return "user/mypage";
        } else {
            // 세션에 customerId가 없으면 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }
    }

    @GetMapping("/user/mypage/data")
    @ResponseBody
    public UserInfoDto getUserInfoData( HttpSession session) {
        String customerId = (String) session.getAttribute("customerId");
        UserInfoDto result = loginService.getCustomerData(customerId);
        return result;
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
