package org.example.shoppingmall.service.order;


import org.example.shoppingmall.dto.order.AddressDto;
import org.example.shoppingmall.dto.order.OrderDto;
import org.example.shoppingmall.dto.order.ProductInfoDto;
import org.example.shoppingmall.repository.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    //주문서 기본배송지 표시
    public AddressDto getDefaultAddress(String customerId) {
        return orderRepository.findDefaultAddressByCustomerId(customerId);
    }

 /*   //배송지 목록 모두 표시
    public List<AddressDto> getAllAddress(String customerId) {
        return orderRepository.findAllAddressByCustomerId(customerId);
    }*/
    
    //주문 상품 정보 표시
    public List<ProductInfoDto> getProductInfoByProductDetailId(List<String> productDetailId, List<Integer> quantity) {
        Map<String, Object> params = new HashMap<>();
        params.put("productDetailId", productDetailId);
        params.put("quantity", quantity);

        // 데이터 조회
        List<ProductInfoDto> productInfoList = orderRepository.findProductInfoByProductDetailId(params);

        // 조회된 상품 정보에 quantity 설정
        for (int i = 0; i < productInfoList.size(); i++) {
            productInfoList.get(i).setQuantity(quantity.get(i));  // 순차적으로 quantity 설정
        }

        return productInfoList;
    }

    // 총 주문 금액 계산
    public BigDecimal calculateTotalAmount(List<ProductInfoDto> productInfo) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (ProductInfoDto product : productInfo) {
            totalAmount = totalAmount.add(product.getTotalPrice());
        }
        return totalAmount;
    }
}
