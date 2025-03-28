package org.example.shoppingmall.dto.user;

import lombok.Data;

@Data
public class DeliveryInfoDto {
    private String addressId;
    private String customerId;;
    private String code;
    private String deliveryName;
    private String address;
    private String detailAddress;
    private String zipCode;
    private int status;
    private String receivePeople;
    private String receivePhoneNumber;


}
