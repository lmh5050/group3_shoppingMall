package org.example.shoppingmall.service.payment;

import org.example.shoppingmall.dto.payment.*;
import org.example.shoppingmall.repository.payment.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        //saveOrder(paymentInfoDto);
        //saveOrderDetails(paymentInfoDto);
        savePayment(paymentInfoDto);
    }

    // 주문 정보 저장
    private void saveOrder(PaymentInfoDto paymentInfoDto) {
        PaymentOrderDto orderDto = PaymentOrderDto.builder()
                .orderId(paymentInfoDto.getOrderId())
                .customerId(paymentInfoDto.getCustomerId())
                .orderStatus(paymentInfoDto.getOrderStatus())
                .totalOrderAmount(paymentInfoDto.getTotalOrderAmount())
                .totalQuantity(paymentInfoDto.getTotalQuantity())
                .totalTypeCnt(paymentInfoDto.getTotalTypeCnt())
                .build();
        paymentRepository.insertOrder(orderDto);
    }

    // 주문 상세 정보 저장
    private void saveOrderDetails(PaymentInfoDto paymentInfoDto) {
        for (PaymentOrderDetailDto detailDto : paymentInfoDto.getOrderDetailList()) {
            PaymentOrderDetailDto orderDetailDto = PaymentOrderDetailDto.builder()
                    .orderDetailId(detailDto.getOrderDetailId())
                    .orderId(paymentInfoDto.getOrderId())
                    .productId(detailDto.getProductId())
                    .productName(detailDto.getProductName())
                    .size(detailDto.getSize())
                    .quantity(detailDto.getQuantity())
                    .price(detailDto.getPrice())
                    .discountAmount(detailDto.getDiscountAmount())
                    .productPrice(detailDto.getProductPrice())
                    .productTotalPrice(detailDto.getProductTotalPrice())
                    .build();
            paymentRepository.insertOrderDetail(orderDetailDto);
        }
    }

    // 결제 정보 저장
    private void savePayment(PaymentInfoDto paymentInfoDto) {
        PaymentDto paymentDto = PaymentDto.builder()
                .paymentId(UUID.randomUUID().toString())
                .orderId(paymentInfoDto.getOrderId())
                .paymentMethod(paymentInfoDto.getPaymentMethod())
                .cardType(paymentInfoDto.getCardType())
                .cardInstallment(paymentInfoDto.getCardInstallment())
                .cardNumber(paymentInfoDto.getCardNumber())
                .cashBankName(paymentInfoDto.getCashBankName())
                .cashReceiptType(paymentInfoDto.getCashReceiptType())
                .cashReceiptNumber(paymentInfoDto.getCashReceiptNumber())
                .build();
        paymentRepository.insertPayment(paymentDto);
    }
}