package org.example.shoppingmall.service.login;

import org.example.shoppingmall.dto.User.InsertUserInfoDto;
import org.example.shoppingmall.dto.User.UserAddressDto;
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
            String addressId = "adr" + '-' + InsertUserInfo.getCustomerId() + '-' + "000"; //어드레스 아이디 만드는 코드
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

    public List<UserAddressDto> getUserAddressInfo(String customerId) {
        List<UserAddressDto> userAddressInfo = userRepository.getUserAddressInfo(customerId);
        return userAddressInfo;
    }

    public void insertUserAddressInfo(UserAddressDto userAddress) {
        System.out.println(userAddress.getCustomerId());
        List<UserAddressDto> UserAddressIdInfo = userRepository.getUserAddressIdInfo(userAddress.getCustomerId());

        // UserAddressIdInfo에서 가장 큰 번호 찾기
        String maxAddressId = "";
        for (UserAddressDto address : UserAddressIdInfo) {
            String addressId = address.getAddressId();  // 예: adr-123-001
            String[] parts = addressId.split("-");  // ["adr", "123", "001"]
            String numberPart = parts[2];  // 숫자 부분 추출, 예: "001"

            // 현재 주소 ID가 기존 최대 주소 ID보다 크면, maxAddressId를 업데이트
            if (maxAddressId.isEmpty() || Integer.parseInt(numberPart) > Integer.parseInt(maxAddressId.split("-")[2])) {
                maxAddressId = addressId;
            }
        }

// 마지막으로 얻은 가장 큰 주소 ID에서 숫자 부분을 분리하여 새로 번호를 추가
        String lastElement = maxAddressId; // 가장 큰 주소 ID
        System.out.println(lastElement + " 아이디");
        String[] parts = lastElement.split("-");
        String numberPart = parts[2];  // "000" 같은 숫자 부분
        System.out.println(numberPart + " 가공 전");
        int newNumber = Integer.parseInt(numberPart) + 1;  // 최대값에 1을 더하기
        System.out.println(newNumber + " 가공 후");
        String newNumberFormatted = String.format("%03d", newNumber);  // 3자리 숫자 포맷
        String addressId = "adr" + '-' + userAddress.getCustomerId() + '-' + newNumberFormatted; // 새로운 주소 ID 생성

// 주소에서 앞 두 문자 추출하여 코드 테이블 값 확인
        String addressCode = userRepository.getAddressCode(userAddress.getAddress().substring(0, 2));
        userAddress.setCode(addressCode);
        userAddress.setAddressId(addressId);

// 주소 정보 삽입
        userRepository.insertUserAddressInfo(userAddress);
        return;
    }

    public void updateDefaultDelivery(UserAddressDto userInfo) {
        userRepository.updateDefaultDeliveryZero(userInfo);
        userRepository.updateDefaultDelivery(userInfo);
        return ;
    }

    public void updateAddressManage(UserAddressDto userInfo) {
        userRepository.updateAddressManage(userInfo);
        return ;
    }

    public void deleteAddress(UserAddressDto userInfo) {
        userRepository.deleteAddress(userInfo);
    }









}

