package org.example.shoppingmall.repository.User;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.User.InsertUserInfoDto;


@Mapper
public interface UserRepository {
    String getCustomerId(String customerId);
    void insertUserInfo(InsertUserInfoDto InsertUserInfo);
    void insertUserDeliveryInfo(InsertUserInfoDto InsertUserInfo);
}
