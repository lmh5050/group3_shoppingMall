package org.example.shoppingmall.repository.order;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.order.AddressDto;
import org.example.shoppingmall.dto.order.OrderDto;
import org.example.shoppingmall.dto.order.ProductInfoDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Mapper
public interface OrderRepository {

    //주문번호 생성
    Long generateOrderId();

    //기본배송지 가져오기
    AddressDto findDefaultAddressByCustomerId(String customerId);

    //상품정보가져오기
    List<ProductInfoDto> findProductInfoByProductDetailId(Map<String, Object> params);




    /*// 전체 주문 목록 조회
    List<OrderDto> findAllOrders();

    // 주문 ID로 주문 상세 조회
    OrderDto findOrderById(String orderId);*/
}
