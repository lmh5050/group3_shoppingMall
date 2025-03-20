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
public class PaymentDto {
    // 결제 정보
    private String paymentId;
    private Integer orderId;
    private String paymentMethod;
    private Integer cardType;
    private Integer cardInstallment;
    private Integer cardNumber;
    private String cashBankName;
    private Integer cashReceiptType;
    private Integer cashReceiptNumber;
}
