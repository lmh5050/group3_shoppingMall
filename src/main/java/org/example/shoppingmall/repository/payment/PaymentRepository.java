package org.example.shoppingmall.repository.payment;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.payment.PaymentDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface PaymentRepository {
    List<PaymentDto> getPaymentData();
    void insertPaymentData(PaymentDto paymentDto);
}
