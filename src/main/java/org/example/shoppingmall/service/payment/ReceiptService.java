package org.example.shoppingmall.service.payment;

import org.example.shoppingmall.dto.payment.*;
import org.example.shoppingmall.repository.payment.PaymentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceiptService {
    private final PaymentRepository paymentRepository;

    public ReceiptService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // 거래명세서 조회
    public ReceiptDto getReceipt(Integer orderId) {
        PaymentOrderDto order = paymentRepository.getOrder(orderId);
        PaymentDto payment = paymentRepository.getPayment(orderId);
        List<PaymentOrderDetailDto> orderDetails = paymentRepository.getOrderDetails(orderId);
        
        String paymentMethod = payment.getPaymentMethodId() == 1 ? "가상계좌" : "신용/체크카드";
        String customerName = paymentRepository.findCustomerNameById(order.getCustomerId());
        
        // 구매 상품 목록 변환
        List<ReceiptProductDto> receiptProducts = orderDetails.stream()
            .map(detail -> ReceiptProductDto.builder()
                .productName(detail.getProductName())
                .color(detail.getColor())
                .size(detail.getSize())
                .quantity(detail.getQuantity())
                .productPrice(detail.getProductPrice() * detail.getQuantity())
                .build())
            .collect(Collectors.toList());

        return ReceiptDto.builder()
                .orderId(order.getOrderId())
                .customerName(customerName)
                .orderDate(payment.getPaymentDatetime())
                .orderDetailList(receiptProducts)
                .totalOrderAmount(order.getTotalOrderAmount())
                .totalDiscountAmount(order.getTotalDiscountAmount())
                .finalAmount(order.getTotalOrderAmount() - order.getTotalDiscountAmount())
                .paymentMethod(paymentMethod)
                .build();
    }
}