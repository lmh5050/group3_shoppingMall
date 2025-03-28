package org.example.shoppingmall.service.payment;

import org.example.shoppingmall.dto.payment.*;
import org.example.shoppingmall.repository.payment.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PaymentAdminService {
    private final PaymentRepository paymentRepository;

    public PaymentAdminService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // 결제 대기 건 조회
    public List<PaymentPendingDto> getPendingPayments() {
        return paymentRepository.getPendingPayments();
    }

    // 결제 상태 변경
    @Transactional
    public void updatePaymentStatus(PaymentPendingDto paymentPendingDto) {
        String orderStatus;

        // 결제 상태 업데이트
        paymentRepository.updatePaymentStatus(paymentPendingDto);
        // 주문 상태 업데이트
        updateOrderStatus(paymentPendingDto);

        // 결제 이력 저장
        insertPaymentHistory(paymentPendingDto);
        // 주문 이력 저장
        insertOrderHistory(paymentPendingDto);
    }

    // 주문 상태 변경
    private void updateOrderStatus(PaymentPendingDto paymentPendingDto) {
        String orderStatus;

        if (paymentPendingDto.getStatus().equals("MA01003")) {
            orderStatus = "MA01006"; // 주문 완료
        } else if (paymentPendingDto.getStatus().equals("MA01004")) {
            orderStatus = "MA01009"; // 주문 취소
        } else {
            orderStatus = "MA01008"; // 주문 중
        }

        paymentRepository.updateOrderStatus(paymentPendingDto.getOrderId(), orderStatus);
    }

    // 결제 이력 저장
    private void insertPaymentHistory(PaymentPendingDto paymentPendingDto) {
        PaymentDto paymentDto = paymentRepository.getPayment(paymentPendingDto.getOrderId());
        paymentDto = paymentDto.toBuilder()
            .status(paymentPendingDto.getStatus())
            .build();
        paymentRepository.insertPaymentHistory(paymentDto);
    }

    // 주문 이력 저장
    private void insertOrderHistory(PaymentPendingDto paymentPendingDto) {
        PaymentOrderDto paymentOrderDto = paymentRepository.getOrder(paymentPendingDto.getOrderId());
        paymentOrderDto = paymentOrderDto.toBuilder()
            .orderStatus(paymentPendingDto.getStatus())
            .build();
        paymentRepository.insertOrderHistory(paymentOrderDto);
    }
}