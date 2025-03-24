package org.example.shoppingmall.service.login;

import org.example.shoppingmall.dto.User.InsertUserInfoDto;
import org.example.shoppingmall.dto.User.UserInfoDto;
import org.example.shoppingmall.dto.User.UserLoginInfoDto;
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

    public LoginService(UserRepository userRepository, PasswordUtillService passwordUtillService) {
        this.userRepository = userRepository;
        this.passwordUtillService = passwordUtillService;
    }

    //서비스 단
    public String getCustomerId(String customerId) {
        String testData = userRepository.getCustomerId(customerId);
        return testData;
    }

    @Transactional
    public String insertUserInfo(InsertUserInfoDto InsertUserInfo) {
        try {
            String encryptedPassword = passwordUtillService.encryptPassword(InsertUserInfo.getPw());
            InsertUserInfo.setPw(encryptedPassword);  // 암호화된 비밀번호와 Salt를 설정
            userRepository.insertUserInfo(InsertUserInfo);
            //여기서 부터 이제 insertUserInfo 필요한 값 생성
            // 1. address_id 생성 > ads-customerId-001 > 처음 생성은 전부다 001 값임
            String addressId = "adr" + '-' + InsertUserInfo.getName() + '-' + "000"; //어드레스 아이디 만드는 코드
            // 2. 주소에서 앞에 두개 따와서 코드 테이블 값 확인하고 그거 대응하는 값 넣어주기 code에
            String addressCode = userRepository.getAddressCode(InsertUserInfo.getAddress().substring(0, 2));
            InsertUserInfo.setCode(addressCode);
            InsertUserInfo.setAddressId(addressId);
            userRepository.insertUserDeliveryInfo(InsertUserInfo);
            return "success";

        } catch (NoSuchAlgorithmException e) {
            // 예외 처리
            e.printStackTrace();
        }
        return "";
    }

    //서비스 단
    public String checkNickname(String nickName) {
        String result = "";
        int searchData = userRepository.checkNickname(nickName);
        if (searchData == 1) {
            result = "사용불가";
        } else {
            result = "사용가능";
        }
        System.out.println(nickName);
        System.out.println(searchData + "결과값");
        return result;
    }

    public boolean verifyPassword(String enteredPassword, String storedPassword) {
        try {
            // PasswordUtillService의 verifyPassword 메서드를 정적(static) 메서드로 호출
            return passwordUtillService.verifyPassword(enteredPassword, storedPassword);
        } catch (NoSuchAlgorithmException e) {
            // 예외 처리
            e.printStackTrace();
            return false;
        }
    }

    public UserLoginInfoDto userLogin(UserLoginInfoDto loginInfo) {
        UserLoginInfoDto loginInfoData = userRepository.userLogin(loginInfo.getCustomerId());
        boolean status = false;
        if (loginInfoData != null) {
            status = verifyPassword(loginInfo.getPw(), loginInfoData.getPw());
        }
        loginInfoData.setStatus(status);
        System.out.println(status);
        return loginInfoData;
    }

    public UserInfoDto getCustomerData(String customerId) {
        UserInfoDto customerData = userRepository.getUserData(customerId);
        return customerData;
    }

    public void uploadProfileImage(UserInfoDto filePath) {
        userRepository.uploadProfileImage(filePath);
        return ;
    }

    public void modifyUserInfo(UserInfoDto userInfo) {
        userInfo.sanitize();
        userRepository.modifyUserInfo(userInfo);
        return ;
    }


}

