package org.example.shoppingmall.dto.User;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserAddressDto {
    private String addressId;
    private String customerId;
    private String code;
    private String deliveryName;
    private String address;
    private String detailAddress;
    private String zipCode;
    private String delivery_request;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int islandOrMountain;
    private String receivePeople;
    private String receivePhoneNumber;
    private int activeFlag;
    private int deleteFlag;

}
