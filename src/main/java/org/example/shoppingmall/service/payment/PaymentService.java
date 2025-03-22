package org.example.shoppingmall.service.payment;

import org.example.shoppingmall.dto.payment.*;
import org.example.shoppingmall.repository.payment.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public void processPayment(PaymentInfoDto paymentInfoDto) {
        saveOrder(paymentInfoDto);
        saveOrderDetails(paymentInfoDto);
        savePayment(paymentInfoDto);
    }

    // 결제 성공 - 주문 정보 저장
    private void saveOrder(PaymentInfoDto paymentInfoDto) {
        PaymentOrderDto orderDto = PaymentOrderDto.builder()
                .orderId(paymentInfoDto.getOrderId())
                .customerId(paymentInfoDto.getCustomerId())
                .orderStatus("MA01006")  // 주문완료
                .totalDiscountAmount(paymentInfoDto.getTotalDiscountAmount())
                .totalOrderAmount(paymentInfoDto.getTotalOrderAmount())
                .totalQuantity(paymentInfoDto.getTotalQuantity())
                .build();
        paymentRepository.insertOrder(orderDto);
    }

    // 결제 성공 - 주문상세 정보 저장
    private void saveOrderDetails(PaymentInfoDto paymentInfoDto) {
        for (PaymentOrderDetailDto orderDetailDto : paymentInfoDto.getOrderDetailList()) {
            PaymentOrderDetailDto newDetailDto = orderDetailDto.toBuilder()
                .orderId(paymentInfoDto.getOrderId())
                .status("MA01006") // 주문완료
                .build();
            paymentRepository.insertOrderDetail(newDetailDto);
        }
    }

    // 결제 성공 - 결제 정보 저장
    private void savePayment(PaymentInfoDto paymentInfoDto) {
        // 마지막 paymentId 조회 후 새로운 ID 생성
        String lastPaymentId = paymentRepository.getLastPaymentId();
        String newPaymentId;
        Integer paymentMethod;
        String status;
        String accountDeposit = null;
        
        if (lastPaymentId == null) {
            newPaymentId = "PAY001";
        } else {
            int lastNumber = Integer.parseInt(lastPaymentId.substring(3));
            newPaymentId = String.format("PAY%03d", lastNumber + 1);
        }

        // 결제 방법에 따른 처리
        if (paymentInfoDto.getPaymentMethod().equals("신용/체크카드")) {
            paymentMethod = paymentInfoDto.getCardType();
            status = "MA01003";  // 결제 완료
        } else {
            paymentMethod = 2;  // 가상계좌
            status = "MA01002";  // 결제 대기
            accountDeposit = generateVirtualAccount();  // 가상계좌 번호 생성
        } 

        PaymentDto paymentDto = PaymentDto.builder()
                .paymentId(newPaymentId)                     // PAY001 형식의 ID
                .orderId(paymentInfoDto.getOrderId())
                .paymentMethodId(paymentMethod)     
                .paymentCode(UUID.randomUUID().toString())   // UUID를 paymentCode로 저장
                .status(status)                              // 결제 상태
                .totalAmount(paymentInfoDto.getTotalOrderAmount())
                .taxAmount(0)
                .discountAmount(0)
                .paymentDatetime(LocalDateTime.now())
                .failureReason(null)
                .cardNumber(paymentInfoDto.getCardNumber())
                .cardInstallment(paymentInfoDto.getCardInstallment())
                .cashBankName(paymentInfoDto.getCashBankName())
                .cashReceiptType(paymentInfoDto.getCashReceiptType())
                .cashReceiptNumber(paymentInfoDto.getCashReceiptNumber())
                .accountDeposit(accountDeposit)              // 가상계좌 번호
                .build();
        paymentRepository.insertPayment(paymentDto);
    }

    // 가상계좌 번호 생성 메서드
    private String generateVirtualAccount() {
        return "1002" + String.format("%010d", (int)(Math.random() * 10000000000L));
    }

    // 영수증 조회
    public PaymentInfoDto getReceipt(Integer orderId) {
        PaymentOrderDto order = paymentRepository.getOrder(orderId);
        List<PaymentOrderDetailDto> orderDetails = paymentRepository.getOrderDetails(orderId);
        PaymentDto payment = paymentRepository.getPayment(orderId);

        return PaymentInfoDto.builder()
                .orderId(order.getOrderId())
                .customerId(order.getCustomerId())
                .orderStatus(order.getOrderStatus())
                .totalDiscountAmount(order.getTotalDiscountAmount())
                .totalOrderAmount(order.getTotalOrderAmount())
                .totalQuantity(order.getTotalQuantity())
                .orderDetailList(orderDetails)
                .paymentMethod(payment.getPaymentMethodId() == 2 ? "가상계좌" : "신용/체크카드")
                .cardType(payment.getPaymentMethodId() == 2 ? null : payment.getPaymentMethodId())
                .cardInstallment(payment.getCardInstallment())
                .cardNumber(payment.getCardNumber())
                .cashBankName(payment.getCashBankName())
                .cashReceiptType(payment.getCashReceiptType())
                .cashReceiptNumber(payment.getCashReceiptNumber())
                .build();
    }
}