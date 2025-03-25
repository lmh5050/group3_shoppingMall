package org.example.shoppingmall.dto.order;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
@Data
public class AddressDto {
    private BigDecimal price;
    private String address;
    private String detailAddress;
    private String zipCode;
    private Integer status;
    private String delivery_request;
    private String receivePeople;
    private String receivePhoneNumber;
}