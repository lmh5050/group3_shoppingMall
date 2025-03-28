package org.example.shoppingmall.dto.payment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)  
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentOrderDetailDto {
    // 주문 상세 정보
    private Integer orderId;
    private Integer serialNumber;
    private String productDetailId;
    private String productName;
    private String size;
    private String color;
    private Integer quantity;
    private Integer price;
    private Integer discountAmount;
    private Integer productPrice;
    private Integer productTotalPrice;
} 