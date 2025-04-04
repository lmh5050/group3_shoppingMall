package org.example.shoppingmall.service.payment;

import org.example.shoppingmall.dto.payment.*;
import org.example.shoppingmall.repository.payment.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    private static final String rightCardNumber = "3000313570007733";
    private static final String orderCompleted = "MA01006";    // 주문 완료
    private static final String orderInProgress = "MA01008";   // 주문 중
    private static final String paymentCompleted = "MA01003";  // 결제 완료
    private static final String paymentInProgress = "MA01002"; // 결제 대기
    private static final String paymentFailed = "MA01005";     // 결제 실패

    // 결제 처리
    @Transactional
    public void processPayment(PaymentInfoDto paymentInfoDto) {
        LocalDateTime now = LocalDateTime.now();  // 현재 시간을 변수로 저장

        if (paymentInfoDto.getPaymentMethod().equals("가상계좌") || paymentInfoDto.getCardNumber().equals(rightCardNumber)) {
            // 결제 정보 저장
            savePayment(paymentInfoDto, now);
            // 주문 관련 정보 저장
            saveOrder(paymentInfoDto, now);
            saveOrderDetails(paymentInfoDto);
            // 배송 정보 저장
            saveShipping(paymentInfoDto);
        } else {
            // 실패하는 경우 결제 정보만 저장
            saveFailedPayment(paymentInfoDto, now);
        }
    }

    // 결제 성공 - 주문 정보 저장
    private void saveOrder(PaymentInfoDto paymentInfoDto, LocalDateTime orderDatetime) {
        String status;
        // 결제 방법에 따른 처리
        if (paymentInfoDto.getPaymentMethod().equals("신용/체크카드")) {
            status = orderCompleted;  // 주문 완료
        } else {
            status = orderInProgress;  // 주문 중
        } 

        PaymentOrderDto orderDto = PaymentOrderDto.builder()
                .orderId(paymentInfoDto.getOrderId())
                .customerId(paymentInfoDto.getCustomerId())
                .orderStatus(status)  // 주문 상태
                .totalDiscountAmount(paymentInfoDto.getTotalDiscountAmount())
                .totalOrderAmount(paymentInfoDto.getTotalOrderAmount())
                .totalQuantity(paymentInfoDto.getTotalQuantity())
                .orderDatetime(orderDatetime)  // 전달받은 시간 설정
                .build();
        
        // 주문 정보 저장
        paymentRepository.insertOrder(orderDto);
        // 주문 이력 저장
        paymentRepository.insertOrderHistory(orderDto);
    }

    // 결제 성공 - 주문상세 정보 저장
    private void saveOrderDetails(PaymentInfoDto paymentInfoDto) {
        for (PaymentOrderDetailDto orderDetailDto : paymentInfoDto.getOrderDetailList()) {
            PaymentOrderDetailDto newDetailDto = orderDetailDto.toBuilder()
                .orderId(paymentInfoDto.getOrderId())
                .build();
            
            // 주문 상세 정보 저장
            paymentRepository.insertOrderDetail(newDetailDto);
            // 주문 상세 이력 저장
            paymentRepository.insertOrderDetailHistory(newDetailDto);
        }
    }

    // 결제 성공 - 결제 정보 저장
    private void savePayment(PaymentInfoDto paymentInfoDto, LocalDateTime paymentDatetime) {
        String newPaymentId;
        Integer paymentMethod;
        String status;
        String accountDeposit = null;
        
        // 결제 ID 생성
        newPaymentId = generatePaymentId();

        // 결제 방법/결과에 따른 처리
        if (paymentInfoDto.getPaymentMethod().equals("신용/체크카드") && paymentInfoDto.getCardNumber().equals(rightCardNumber)) {
            paymentMethod = paymentInfoDto.getCardType();
            status = paymentCompleted;  // 결제 완료
        } else {
            paymentMethod = 1;  // 가상계좌
            status = paymentInProgress;  // 결제 대기
            accountDeposit = generateVirtualAccount();  // 가상계좌 번호 생성
        } 

        PaymentDto paymentDto = PaymentDto.builder()
                .paymentId(newPaymentId)                     // PAY001 형식의 ID
                .orderId(paymentInfoDto.getOrderId())
                .customerId(paymentInfoDto.getCustomerId())
                .paymentMethodId(paymentMethod)     
                .paymentCode(UUID.randomUUID().toString())   // UUID를 paymentCode로 저장
                .status(status)                              // 결제 상태
                .finalAmount(paymentInfoDto.getTotalOrderAmount())
                .taxAmount((int)Math.round(paymentInfoDto.getTotalOrderAmount() * 0.1))
                .discountAmount(0)
                .paymentDatetime(paymentDatetime)           // 전달받은 시간 사용
                .failureReason(null)
                .cardNumber(paymentInfoDto.getCardNumber())
                .cardInstallment(paymentInfoDto.getCardInstallment())
                .cashBankName(paymentInfoDto.getCashBankName())
                .cashReceiptType(paymentInfoDto.getCashReceiptType())
                .cashReceiptNumber(paymentInfoDto.getCashReceiptNumber())
                .accountDeposit(accountDeposit)              // 가상계좌 번호
                .build();
        
        // 결제 정보 저장
        paymentRepository.insertPayment(paymentDto);
        // 결제 이력 저장
        paymentRepository.insertPaymentHistory(paymentDto);
    }

    // 결제 실패 - 결제 정보 저장
    private void saveFailedPayment(PaymentInfoDto paymentInfoDto, LocalDateTime paymentDatetime) {
        String newPaymentId = generatePaymentId();

        PaymentDto failedPayment = PaymentDto.builder()
                .paymentId(newPaymentId)
                .orderId(0)
                .customerId(paymentInfoDto.getCustomerId())
                .paymentMethodId(paymentInfoDto.getCardType())
                .paymentCode(UUID.randomUUID().toString())
                .status(paymentFailed)  // 결제 실패
                .finalAmount(paymentInfoDto.getTotalOrderAmount())
                .taxAmount((int)Math.round(paymentInfoDto.getTotalOrderAmount() * 0.1))
                .discountAmount(0)
                .paymentDatetime(paymentDatetime)
                .failureReason("카드 결제 실패")
                .cardNumber(paymentInfoDto.getCardNumber())
                .cardInstallment(paymentInfoDto.getCardInstallment())
                .build();
        
        // 결제 정보 저장
        paymentRepository.insertPayment(failedPayment);
        // 결제 이력 저장
        paymentRepository.insertPaymentHistory(failedPayment);
    }

    // 배송 정보 저장
    private void saveShipping(PaymentInfoDto paymentInfoDto) {
        String newShippingId = generateShippingId();
        
        PaymentShippingDto shippingDto = PaymentShippingDto.builder()
                .shippingId(newShippingId)
                .orderId(paymentInfoDto.getOrderId())
                .basicAddress(paymentInfoDto.getBasicAddress())
                .detailAddress(paymentInfoDto.getDetailAddress())
                .receivePeople(paymentInfoDto.getReceivePeople())
                .receivePhoneNumber(paymentInfoDto.getReceivePhoneNumber())
                .shippingPrice(paymentInfoDto.getShippingPrice())
                .deliveryRequest(paymentInfoDto.getDeliveryRequest())
                .zipCode(paymentInfoDto.getZipCode())
                .build();
        
        // 배송 정보 저장
        paymentRepository.insertShipping(shippingDto);
        // 배송 이력 저장
        paymentRepository.insertShippingHistory(shippingDto);
    }

    // 가상계좌 번호 생성 메서드
    private String generateVirtualAccount() {
        return "1002" + String.format("%010d", (int)(Math.random() * 10000000000L));
    }

    // 결제 ID 생성 메서드
    private String generatePaymentId() {
        String lastPaymentId = paymentRepository.getLastPaymentId();
        String newPaymentId;

        if (lastPaymentId == null) {
            newPaymentId = "PAY001";
        } else {
            int lastNumber = Integer.parseInt(lastPaymentId.substring(3));
            newPaymentId = String.format("PAY%03d", lastNumber + 1);
        }
        return newPaymentId;
    }

    // 배송 ID 생성 메서드
    private String generateShippingId() {
        String lastShippingId = paymentRepository.getLastShippingId();
        String newShippingId;

        if (lastShippingId == null) {
            newShippingId = "SHP001";
        } else {
            int lastNumber = Integer.parseInt(lastShippingId.substring(3));
            newShippingId = String.format("SHP%03d", lastNumber + 1);
        }
        return newShippingId;
    }
}