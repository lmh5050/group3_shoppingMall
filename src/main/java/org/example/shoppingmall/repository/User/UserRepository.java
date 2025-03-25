package org.example.shoppingmall.repository.User;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.User.InsertUserInfoDto;
import org.example.shoppingmall.dto.User.UserAddressDto;
import org.example.shoppingmall.dto.User.UserInfoDto;
import org.example.shoppingmall.dto.User.UserLoginInfoDto;

import java.util.List;


@Mapper
public interface UserRepository {
    String getCustomerId(String customerId);
    void insertUserInfo(InsertUserInfoDto InsertUserInfo);
    void insertUserDeliveryInfo(InsertUserInfoDto InsertUserInfo);
    int checkNickname(String nickName);
    UserLoginInfoDto userLogin(String CustomerId);
    String getAddressCode(String addressName);
    UserInfoDto getUserData (String customerId);
    void modifyUserInfo(UserInfoDto userInfoDto);
    void uploadProfileImage (UserInfoDto userinfo);
    List<UserAddressDto> getUserAddressInfo (String customerId);
    List<UserAddressDto> getUserAddressIdInfo (String customerId);
    void insertUserAddressInfo (UserAddressDto userAddress);
    void updateDefaultDelivery (UserAddressDto userAddress);
    void updateDefaultDeliveryZero (UserAddressDto userAddress);
    void updateAddressManage (UserAddressDto userAddress);
    void deleteAddress (UserAddressDto userAddress);
}
