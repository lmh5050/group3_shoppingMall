package org.example.shoppingmall.repository.payment;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.payment.*;

import java.util.List;

@Mapper
public interface PaymentRepository {
    // 주문번호로 마지막 결제 정보 조회
    PaymentDto findLastPaymentByOrderId(Integer orderId);

    // 마지막 결제 ID 조회
    String getLastPaymentId();

    // 주문 정보 조회
    PaymentOrderDto getOrder(Integer orderId);

    // 주문 상세 정보 조회
    List<PaymentOrderDetailDto> getOrderDetails(Integer orderId);

    // 결제 정보 조회
    PaymentDto getPayment(Integer orderId);

    // 고객 이름 조회
    String findCustomerNameById(String customerId);
    
    // 결제 대기 건 조회
    List<PaymentPendingDto> getPendingPayments();

    // 마지막 배송 ID 조회
    String getLastShippingId();

    // 주문 정보 저장
    void insertOrder(PaymentOrderDto orderDto);
    
    // 주문 상세 정보 저장
    void insertOrderDetail(PaymentOrderDetailDto orderDetailDto);
    
    // 결제 정보 저장
    void insertPayment(PaymentDto paymentDto);

    // 결제 이력 저장
    void insertPaymentHistory(PaymentDto paymentDto);

    // 주문 이력 저장
    void insertOrderHistory(PaymentOrderDto orderDto);

    // 주문 상세 이력 저장
    void insertOrderDetailHistory(PaymentOrderDetailDto orderDetailDto);

    // 배송 정보 저장
    void insertShipping(PaymentShippingDto shippingDto);

    // 배송 이력 저장
    void insertShippingHistory(PaymentShippingDto shippingDto);

    // 결제 정보 변경
    void updatePayment(PaymentDto paymentDto);

    // 결제 상태 변경
    void updatePaymentStatus(PaymentPendingDto paymentPendingDto);

    // 주문 상태 변경
    void updateOrderStatus(Integer orderId, String status);
}