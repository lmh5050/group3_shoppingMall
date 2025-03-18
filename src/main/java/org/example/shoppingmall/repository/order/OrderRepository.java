package org.example.shoppingmall.repository.order;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.order.OrderDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderRepository {

    // 주문 저장
    void saveOrder(OrderDto orderDto);

    // 전체 주문 목록 조회
    List<OrderDto> findAllOrders();

    // 주문 ID로 주문 상세 조회
    OrderDto findOrderById(String orderId);
}
