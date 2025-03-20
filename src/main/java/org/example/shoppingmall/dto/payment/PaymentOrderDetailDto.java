package org.example.shoppingmall.dto.payment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentOrderDetailDto {
    // 주문 상세 정보
    private Integer orderDetailId;
    private Integer orderId;
    private String productId;
    private String productName;
    private String size;
    private Integer quantity;
    private Integer price;
    private Integer discountAmount;
    private Integer productPrice;
    private Integer productTotalPrice;
} 