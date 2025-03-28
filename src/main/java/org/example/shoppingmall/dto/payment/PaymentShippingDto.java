package org.example.shoppingmall.dto.payment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentShippingDto {
    // 배송 정보
    private String shippingId;
    private Integer orderId;
    private String basicAddress;
    private String detailAddress;
    private String receivePeople;
    private String receivePhoneNumber;
    private Integer shippingPrice;
    private String deliveryRequest;
    private Integer zipCode;
} 