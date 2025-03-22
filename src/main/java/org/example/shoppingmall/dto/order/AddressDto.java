package org.example.shoppingmall.dto.order;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class AddressDto {
    private String addressId;
    private String customerId;
    private String code;
    private String deliveryName;
    private String address;
    private String detailAddress;
    private String zipCode;
    private Integer status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Byte islandOrMountain;
    private String receivePeople;
    private String receivePhoneNumber;
    private Byte activeFlag;
    private Byte deleteFlag;
}