package org.example.shoppingmall.repository.payment;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.payment.*;

import java.util.List;

@Mapper
public interface PaymentRepository {
    // 주문 정보 저장
    void insertOrder(PaymentOrderDto orderDto);
    
    // 주문 상세 정보 저장
    void insertOrderDetail(PaymentOrderDetailDto orderDetailDto);
    
    // 결제 정보 저장
    void insertPayment(PaymentDto paymentDto);
}