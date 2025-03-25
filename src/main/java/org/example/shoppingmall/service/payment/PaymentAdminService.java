package org.example.shoppingmall.service.payment;

import org.example.shoppingmall.dto.payment.*;
import org.example.shoppingmall.repository.payment.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentAdminService {
    private final PaymentRepository paymentRepository;

    public PaymentAdminService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // 결제 대기 건 조회
    public List<PaymentPendingDto> getPendingPayments() {
        List<PaymentPendingDto> pendingPayments = paymentRepository.getPendingPayments();
        return pendingPayments;
    }

    // 결제 상태 변경
    public void updatePaymentStatus(PaymentPendingDto paymentPendingDto) {
        paymentRepository.updatePaymentStatus(paymentPendingDto);
    }
}