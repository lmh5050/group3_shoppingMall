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
public class PaymentDto {
    // 결제 정보
    private String paymentId;
    private Integer orderId;
    private Integer paymentMethodId;
    private String paymentCode;
    private String status;
    private Integer totalAmount;
    private Integer taxAmount;
    private Integer discountAmount;
    private LocalDateTime paymentDatetime;
    private String failureReason;   
    private Integer cardNumber;
    private Integer cardInstallment;
    private String cashBankName;    
    private Integer cashReceiptType;
    private Integer cashReceiptNumber;
    private String accountDeposit;  // 가상계좌 입금 계좌번호
}
