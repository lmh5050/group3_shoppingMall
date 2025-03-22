package org.example.shoppingmall.dto.User;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class InsertUserInfoDto {
    private String customerId ;
    private String pw;
    private String email;
    private String name;
    private String nickName;
    private String phoneNumber;
    private Timestamp birth;
    private String recommendId;
    private String addressId;
    private String code;
    private String zipCode;
    private String address;
    private String detailAddress;


}
