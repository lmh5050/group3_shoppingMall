package org.example.shoppingmall.dto.payment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentOrderDto {
    // 주문 정보
    private Integer orderId;
    private String customerId;
    private String orderStatus;
    private Integer totalDiscountAmount;
    private Integer totalOrderAmount;
    private Integer totalQuantity;
    private LocalDateTime orderDatetime;
} 