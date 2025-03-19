package org.example.shoppingmall.repository.payment;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.payment.PaymentDto;
import org.example.shoppingmall.dto.payment.PaymentInfoDto;

import java.util.List;

@Mapper
public interface PaymentRepository {
    List<PaymentDto> getPaymentData();
    void insertPaymentData(PaymentDto paymentDto);

    // 결제하기 페이지 데이터 조회
    PaymentInfoDto findById(String orderId);
}