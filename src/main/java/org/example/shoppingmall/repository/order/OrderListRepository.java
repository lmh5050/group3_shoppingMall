package org.example.shoppingmall.repository.order;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.order.OrderDetailDto;
import org.example.shoppingmall.dto.order.OrderDto;
import org.example.shoppingmall.dto.order.OrderListDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderListRepository {

    //전체 주문목록 조회(관리자)
    List<OrderDto> selectOrderAll();

    //주문목록
    List<OrderListDto> findOrderListByCustomerId(String customerId);

    //주문상세
    List<OrderDetailDto> findOrderDetailByOrderId(Long orderId);

    //주문삭제
    void deleteOrder(Map<String, Object> params);
    void deletePayment(Long orderId);
    void deleteShipping(Long orderId);
}
