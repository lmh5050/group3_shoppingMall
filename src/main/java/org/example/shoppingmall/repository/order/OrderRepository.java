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

    //배송지 모두 가져오기
    List<AddressDto> findAllAddressByCustomerId(String customerId);

    //상품정보가져오기
    List<ProductInfoDto> findProductInfoByProductDetailId(Map<String, Object> params);

}
