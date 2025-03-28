package org.example.shoppingmall.service.order;

import jakarta.servlet.http.HttpSession;
import org.example.shoppingmall.dto.order.OrderDetailDto;
import org.example.shoppingmall.dto.order.OrderDto;
import org.example.shoppingmall.dto.order.OrderListDto;
import org.example.shoppingmall.repository.order.OrderListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderListService {

    @Autowired
    private OrderListRepository orderListRepository;

    //전체 주문목록 조회(관리자)
    public List<OrderDto> getOrderAll() {
        return orderListRepository.selectOrderAll();
    }

    //주문목록
    public List<OrderListDto> getOrderListByCustomerId(String customerId) {
        return orderListRepository.findOrderListByCustomerId(customerId);
    }
    //주문상세
    public List<OrderDetailDto> getOrderDetailByOrderId(Long orderId) {
        return orderListRepository.findOrderDetailByOrderId(orderId);
    }

    //주문삭제
    @Transactional
    public void deleteOrder(Long orderId, String customerId) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("customerId", customerId);
        orderListRepository.deleteOrder(params);
        orderListRepository.deletePayment(orderId);
        orderListRepository.deleteShipping(orderId);
    }
}
