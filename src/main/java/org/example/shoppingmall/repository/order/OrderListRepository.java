package org.example.shoppingmall.repository.order;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.order.OrderDetailDto;
import org.example.shoppingmall.dto.order.OrderListDto;

import java.util.List;

@Mapper
public interface OrderListRepository {
    List<OrderListDto> findOrderListByCustomerId(String customerId);
   /* OrderDetailDto findOrderDetailByOrderId(Long orderId);*/
    List<OrderDetailDto> findOrderDetailByOrderId(Long orderId);
}
