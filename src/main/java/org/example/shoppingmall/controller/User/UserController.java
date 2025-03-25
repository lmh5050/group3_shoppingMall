package org.example.shoppingmall.controller.User;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.example.shoppingmall.dto.User.*;
import org.example.shoppingmall.dto.order.AddressDto;
import org.example.shoppingmall.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.example.shoppingmall.service.login.EmailService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @PostMapping("/user/modify") //회원 정보 수정하는 api
    @ResponseBody //axios json 이용하기 위해서 responseBody 추가
    public void modifyUserInfo(@RequestBody UserInfoDto userInfo) {

        // 아이디 확인
        loginService.modifyUserInfo(userInfo);
        return ; // 결과 반환
    }

    @PostMapping("/user/uploadProfileImage")
    @ResponseBody
    public String uploadProfileImage(@RequestParam("profileImage") MultipartFile file,
                                     HttpSession session) {
        try {
            // 파일 이름 확인
            String fileName = file.getOriginalFilename();
            if (fileName == null || fileName.isEmpty()) {
                return "error"; // 파일 이름이 없는 경우 처리
            }

            // MIME 타입 체크 (PNG만 허용)
            String contentType = file.getContentType();
            if (!"image/png".equals(contentType)) {
                return "error"; // PNG가 아닌 경우 에러 처리
            }

            // 세션에서 customerId 가져오기
            String customerId = (String) session.getAttribute("customerId");
            if (customerId == null) {
                return "error"; // 세션에 customerId가 없는 경우 처리
            }

            // 파일 이름 중복 방지 + 원본 확장자 유지
            String extension = fileName.substring(fileName.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + "_" + customerId + extension;

            // 파일 저장 경로 설정
            String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images/user/";
            File dir = new File(uploadDirectory);
            if (!dir.exists()) {
                dir.mkdirs(); // 경로 없으면 생성
            }

            // 파일 저장
            File destFile = new File(uploadDirectory, uniqueFileName);
            file.transferTo(destFile);

            // 사용자 정보에 프로필 이미지 경로 저장
            UserInfoDto userinfo = new UserInfoDto();
            userinfo.setProfileImg(uniqueFileName);
            userinfo.setCustomerId(customerId);

            // 프로필 이미지 업로드 처리
            loginService.uploadProfileImage(userinfo);

            return "success"; // 업로드 성공 시 반환할 값
        } catch (IOException e) {
            e.printStackTrace();
            return "error"; // 실패 시 반환할 값
        }
    }


    @GetMapping("/user/addressmanage")
    public String getUserAddress(HttpSession session, Model model) {
        String customerId = (String) session.getAttribute("customerId");
        if (customerId == null) {
            return "error"; // 세션에 customerId가 없는 경우 처리
        }
        model.addAttribute("customerId", customerId); // customerId 값을 모델에 추가
        return "user/addressmanage"; // user/addressmanage.html 템플릿 반환
    }


    @GetMapping("/user/addressmanage/{customerId}")
    @ResponseBody
    public List<UserAddressDto> getUserAddressInfo(@PathVariable String customerId) {
        List<UserAddressDto> userinfo = loginService.getUserAddressInfo(customerId);
        return userinfo;
    }

    @PostMapping("/user/addressmanage")
    @ResponseBody
    public String insertUserAddressInfo(@RequestBody UserAddressDto UserAddress ,
                                                HttpSession session) {
        String customerId = (String) session.getAttribute("customerId");
        UserAddress.setCustomerId(customerId);
        loginService.insertUserAddressInfo(UserAddress);
        return "success";
    }

    @PostMapping("/user/addressmanage/select")
    @ResponseBody
    public String updateDefaultDelivery(@RequestBody UserAddressDto UserAddress ,
                                        HttpSession session) {
        String customerId = (String) session.getAttribute("customerId");
        UserAddress.setCustomerId(customerId);
        loginService.updateDefaultDelivery(UserAddress);
        return "success";
    }

    @PostMapping("/user/addressmanage/update")
    @ResponseBody
    public void updateAddressManage(@RequestBody UserAddressDto UserAddress ,
                                        HttpSession session) {
        String customerId = (String) session.getAttribute("customerId");
        UserAddress.setCustomerId(customerId);
        loginService.updateAddressManage(UserAddress);
    }

    @PostMapping("/user/addressmanage/delete")
    @ResponseBody
    public void deleteAddress(@RequestBody UserAddressDto UserAddress ) {
        loginService.deleteAddress(UserAddress);
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
