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
public class PaymentPendingDto {
    private String paymentId;
    private Integer orderId;
    private String customerId;
    private String customerName;
    private Integer finalAmount;
    private LocalDateTime paymentDatetime;
    private String accountDeposit;
    private String cashBankName;
    private String status;
} 