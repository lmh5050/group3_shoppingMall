package org.example.shoppingmall.service.payment;

import org.example.shoppingmall.dto.payment.PaymentDto;
import org.example.shoppingmall.dto.payment.PaymentInfoDto;
import org.example.shoppingmall.repository.payment.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // get 테스트
    public List<PaymentDto> getPayments() {
        return paymentRepository.getPaymentData();
    }
    // post 테스트
    public void createPayment(PaymentDto paymentDto) {
        paymentRepository.insertPaymentData(paymentDto);
    }

    // 주문 정보 get
    public PaymentInfoDto getOrderDetails(String orderId) {
        return paymentRepository.findById(orderId);
    }
}