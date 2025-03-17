package org.example.shoppingmall.service.payment;

import org.example.shoppingmall.dto.payment.PaymentDto;
import org.example.shoppingmall.repository.payment.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<PaymentDto> getPayments() {
        return paymentRepository.getPaymentData();
    }

    public void createPayment(PaymentDto paymentDto) {
        paymentRepository.insertPaymentData(paymentDto);
    }
}
