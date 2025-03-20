package org.example.shoppingmall.service.order;


import org.example.shoppingmall.dto.order.AddressDto;
import org.example.shoppingmall.dto.order.OrderDto;
import org.example.shoppingmall.dto.order.ProductInfoDto;
import org.example.shoppingmall.repository.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    //주문번호 표시
    public Long getOrderId() {
        return orderRepository.generateOrderId();
    }

    //주문서 기본배송지 표시
    public AddressDto getDefaultAddress(String customerId) {
        return orderRepository.findDefaultAddressByCustomerId(customerId);
    }

    //주문 상품 정보 표시
    public List<ProductInfoDto> getProductInfoByProductDetailId(List<String> productDetailId, List<Integer> quantity) {
        // 파라미터를 Map에 담아서 쿼리 호출
        Map<String, Object> params = new HashMap<>();
        params.put("productDetailId", productDetailId);
        params.put("quantity", quantity);

        // 데이터 조회
        List<ProductInfoDto> productInfoList = orderRepository.findProductInfoByProductDetailId(params);

        // 주문 일련번호 부여
        for (int i = 0; i < productInfoList.size(); i++) {
            productInfoList.get(i).setOrderDetailId(i + 1);  // 순차적으로 번호를 부여
        }

        return productInfoList;
    }

   /* public List<ProductInfoDto> getProductInfoByProductDetailId(List<String> productDetailId, List<Integer> quantity) {
        Map<String, Object> params = new HashMap<>();
        params.put("productDetailId", productDetailId);
        params.put("quantity", quantity);
        return orderRepository.findProductInfoByProductDetailId(params);
    }*/





 /*   // 주문 목록 조회
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAllOrders();
    }

    // 주문 상세 조회
    public OrderDto getOrderDetail(String orderId) {
        return orderRepository.findOrderById(orderId);
    }*/




}
