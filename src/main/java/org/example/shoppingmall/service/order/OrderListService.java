package org.example.shoppingmall.service.order;

import org.example.shoppingmall.dto.order.OrderDetailDto;
import org.example.shoppingmall.dto.order.OrderListDto;
import org.example.shoppingmall.repository.order.OrderListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderListService {

    @Autowired
    private OrderListRepository orderListRepository;

    public List<OrderListDto> getOrderListByCustomerId(String customerId) {
        return orderListRepository.findOrderListByCustomerId(customerId);
    }

    public List<OrderDetailDto> getOrderDetailByOrderId(Long orderId) {
        return orderListRepository.findOrderDetailByOrderId(orderId);
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        orderListRepository.deleteOrder(orderId);
        orderListRepository.deleteShipping(orderId);
    }

}
