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
        // 결제 상태 업데이트
        paymentRepository.updatePaymentStatus(paymentPendingDto);
        
        // 결제 정보 조회
        PaymentDto paymentDto = paymentRepository.getPayment(paymentPendingDto.getOrderId());
        System.out.println(paymentDto.getFinalAmount());
        // status만 업데이트
        paymentDto = paymentDto.toBuilder()
            .status(paymentPendingDto.getStatus())
            .build();
        
        // 결제 이력 저장
        paymentRepository.insertPaymentHistory(paymentDto);
    }
}