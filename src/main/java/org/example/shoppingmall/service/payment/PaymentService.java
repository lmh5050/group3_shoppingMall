package org.example.shoppingmall.service.payment;

import org.example.shoppingmall.dto.payment.*;
import org.example.shoppingmall.repository.payment.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    private int rightCardNumber = 1234;

    // 결제 처리
    @Transactional
    public void processPayment(PaymentInfoDto paymentInfoDto) {
        // 이전 결제 내역 조회
        PaymentDto existingPayment = paymentRepository.findLastPaymentByOrderId(paymentInfoDto.getOrderId());
        
        if (existingPayment != null && "MA01005".equals(existingPayment.getStatus())) {
            // 실패한 결제 내역이 있는 경우 재시도 처리
            retryPayment(paymentInfoDto, existingPayment);
        } else {
            // 새로운 결제 처리
            processNewPayment(paymentInfoDto);
        }
    }

    // 새로운 결제 처리
    private void processNewPayment(PaymentInfoDto paymentInfoDto) {
        if (paymentInfoDto.getPaymentMethod().equals("가상계좌") || paymentInfoDto.getCardNumber() == rightCardNumber) {
            saveOrder(paymentInfoDto);
            saveOrderDetails(paymentInfoDto);
            savePayment(paymentInfoDto);
        } else {
            savePayment(paymentInfoDto);
        }
    }

    // 결제 재시도 처리
    private void retryPayment(PaymentInfoDto paymentInfoDto, PaymentDto existingPayment) {
        if (paymentInfoDto.getCardNumber() == rightCardNumber) {
            // 기존 결제 정보 상태 업데이트
            PaymentDto updatedPayment = PaymentDto.builder()
                    .paymentId(existingPayment.getPaymentId())
                    .paymentCode(UUID.randomUUID().toString())
                    .status("MA01003")  // 결제 완료로 상태 변경
                    .paymentDatetime(LocalDateTime.now())
                    .failureReason("결제 재시도 성공")
                    .cardNumber(paymentInfoDto.getCardNumber())
                    .cardInstallment(paymentInfoDto.getCardInstallment())
                    .build();
            
            paymentRepository.updatePayment(updatedPayment);
            
            // 주문 정보와 주문 상세 정보 저장
            saveOrder(paymentInfoDto);
            saveOrderDetails(paymentInfoDto);
        } else {
            // 재시도 실패 시 상태만 업데이트
            PaymentDto failedPayment = PaymentDto.builder()
                    .paymentId(existingPayment.getPaymentId())
                    .status("MA01005")  // 결제 실패
                    .paymentDatetime(LocalDateTime.now())
                    .failureReason("카드 결제 실패")
                    .build();
            
            paymentRepository.updatePayment(failedPayment);
        }
    }

    // 결제 성공 - 주문 정보 저장
    private void saveOrder(PaymentInfoDto paymentInfoDto) {
        String status;
        // 결제 방법에 따른 처리
        if (paymentInfoDto.getPaymentMethod().equals("신용/체크카드")) {
            status = "MA01006";  // 주문 완료
        } else {
            status = "MA01008";  // 주문 중
        } 

        PaymentOrderDto orderDto = PaymentOrderDto.builder()
                .orderId(paymentInfoDto.getOrderId())
                .customerId(paymentInfoDto.getCustomerId())
                .orderStatus(status)  // 주문 상태
                .totalDiscountAmount(paymentInfoDto.getTotalDiscountAmount())
                .totalOrderAmount(paymentInfoDto.getTotalOrderAmount())
                .totalQuantity(paymentInfoDto.getTotalQuantity())
                .build();
        paymentRepository.insertOrder(orderDto);
    }

    // 결제 성공 - 주문상세 정보 저장
    private void saveOrderDetails(PaymentInfoDto paymentInfoDto) {
        String status;
        if (paymentInfoDto.getPaymentMethod().equals("신용/체크카드")) {
            status = "MA01006";  // 주문 완료
        } else {
            status = "MA01008";  // 주문 중
        }

        for (PaymentOrderDetailDto orderDetailDto : paymentInfoDto.getOrderDetailList()) {
            PaymentOrderDetailDto newDetailDto = orderDetailDto.toBuilder()
                .orderId(paymentInfoDto.getOrderId())
                .status(status)  // 주문 상태
                .build();
            paymentRepository.insertOrderDetail(newDetailDto);
        }
    }

    // 결제 정보 저장
    private void savePayment(PaymentInfoDto paymentInfoDto) {
        String lastPaymentId = paymentRepository.getLastPaymentId();
        String newPaymentId;
        Integer paymentMethod;
        String status;
        String accountDeposit = null;
        String failureReason = null;
        
        // 결제 ID 생성
        if (lastPaymentId == null) {
            newPaymentId = "PAY001";
        } else {
            int lastNumber = Integer.parseInt(lastPaymentId.substring(3));
            newPaymentId = String.format("PAY%03d", lastNumber + 1);
        }

        // 결제 방법/결과에 따른 처리
        if (paymentInfoDto.getPaymentMethod().equals("신용/체크카드") && paymentInfoDto.getCardNumber() == rightCardNumber) {
            paymentMethod = paymentInfoDto.getCardType();
            status = "MA01003";  // 결제 완료
        } else if (paymentInfoDto.getPaymentMethod().equals("신용/체크카드")) {
            paymentMethod = paymentInfoDto.getCardType();
            status = "MA01005";  // 결제 실패
            failureReason = "카드 결제 실패";
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
                .failureReason(failureReason)
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
}