package org.example.shoppingmall.dto.payment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentInfoDto {
    // 주문 정보
    private Integer orderId;
    private String customerId;
    private String orderStatus;
    private Integer totalDiscountAmount;
    private Integer totalOrderAmount;
    private Integer totalQuantity;
    // 주문 상세 정보
    private List<PaymentOrderDetailDto> orderDetailList;
    // 배송 정보
    private String basicAddress;
    private String detailAddress;
    private String receivePeople;
    private String receivePhoneNumber;
    private Integer shippingPrice;
    private String deliveryRequest;
    private Integer zipCode;
    // 결제 정보
    private String paymentMethod;
    private Integer cardType;
    private Integer cardInstallment;
    private Integer cardNumber;
    private String cashBankName;
    private Integer cashReceiptType;
    private Integer cashReceiptNumber;
}
