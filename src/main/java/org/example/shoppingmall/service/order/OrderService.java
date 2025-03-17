package org.example.shoppingmall.service.order;


import org.example.shoppingmall.dto.order.OrderDto;
import org.example.shoppingmall.repository.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // 주문 생성
    public void createOrder(OrderDto orderDto) {
        orderRepository.saveOrder(orderDto);
    }

    // 주문 목록 조회
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAllOrders();
    }

    // 주문 상세 조회
    public OrderDto getOrderDetail(String orderId) {
        return orderRepository.findOrderById(orderId);
    }
}
