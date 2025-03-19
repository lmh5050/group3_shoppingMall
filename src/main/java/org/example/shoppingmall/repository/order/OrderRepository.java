package org.example.shoppingmall.repository.order;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.order.AddressDto;
import org.example.shoppingmall.dto.order.OrderDto;
import org.example.shoppingmall.dto.order.ProductInfoDto;

import java.util.ArrayList;


@Mapper
public interface OrderRepository {

    // 주문 저장
    void saveOrder(OrderDto orderDto);

    //기본배송지 가져오기
    AddressDto findDefaultAddressByCustomerId(String customerId);

    //상품정보가져오기
    ProductInfoDto findProductInfoByProductDetailId(String productDetailId);


    /*// 전체 주문 목록 조회
    List<OrderDto> findAllOrders();

    // 주문 ID로 주문 상세 조회
    OrderDto findOrderById(String orderId);*/
}
