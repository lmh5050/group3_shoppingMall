package org.example.shoppingmall.service.order;

import org.example.shoppingmall.dto.order.AddressDto;
import org.example.shoppingmall.dto.order.ProductInfoDto;
import org.example.shoppingmall.repository.order.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    private AddressDto mockAddress;
    private List<ProductInfoDto> mockProductInfo;

    @BeforeEach
    void setUp() {
        // 기본 배송지 설정
        mockAddress = new AddressDto();
        mockAddress.setReceivePeople("김자바의정석");
        mockAddress.setAddress("서울시 강남구");
        mockAddress.setDetailAddress("123-45");
        mockAddress.setZipCode("12345");
        mockAddress.setReceivePhoneNumber("010-1234-5678");
        mockAddress.setStatus(2);  // 기본 배송지 상태 설정

        // 상품 정보 설정
        ProductInfoDto product1 = new ProductInfoDto();
        product1.setName("테스트 상품1");
        product1.setPrice(new BigDecimal("20000"));
        product1.setQuantity(2);

        ProductInfoDto product2 = new ProductInfoDto();
        product2.setName("테스트 상품2");
        product2.setPrice(new BigDecimal("10000"));
        product2.setQuantity(1);

        mockProductInfo = Arrays.asList(product1, product2);
    }

    //주소지 테스트
    @Test
    public void addressTest() {
        String customerId = "testCustomer";
        when(orderRepository.findDefaultAddressByCustomerId(customerId)).thenReturn(mockAddress);

        AddressDto address = orderService.getDefaultAddress(customerId);

        assertEquals("김자바의정석", address.getReceivePeople());
        assertEquals("서울시 강남구", address.getAddress());
        assertEquals("123-45", address.getDetailAddress());
        assertEquals("12345", address.getZipCode());
        assertEquals("010-1234-5678", address.getReceivePhoneNumber());
        assertEquals(2, address.getStatus());  // 기본 배송지 상태 확인

        System.out.println("수신인: " + address.getReceivePeople());
        System.out.println("주소: " + address.getAddress());
        System.out.println("상세주소: " + address.getDetailAddress());
        System.out.println("우편번호: " + address.getZipCode());
        System.out.println("휴대폰번호: " + address.getReceivePhoneNumber());
        System.out.println("기본배송지 여부: " + (address.getStatus() == 1 ? "기본배송지" : "추가배송지"));
    }

    //상품 정보 가져오기 테스트
    @Test
    public void productInfoTest() {
        List<String> productDetailIds = Arrays.asList("PD001", "PD002");
        List<Integer> quantities = Arrays.asList(2, 1);
        when(orderRepository.findProductInfoByProductDetailId(any())).thenReturn(mockProductInfo);

        List<ProductInfoDto> productInfo = orderService.getProductInfoByProductDetailId(productDetailIds, quantities);

        assertNotNull(productInfo);
        assertEquals(2, productInfo.size());
        
        // 첫 번째 상품
        assertEquals("테스트 상품1", productInfo.get(0).getName());
        assertEquals(new BigDecimal("20000"), productInfo.get(0).getPrice());
        assertEquals(2, productInfo.get(0).getQuantity());
        
        // 두 번째 상품
        assertEquals("테스트 상품2", productInfo.get(1).getName());
        assertEquals(new BigDecimal("10000"), productInfo.get(1).getPrice());
        assertEquals(1, productInfo.get(1).getQuantity());


        for (ProductInfoDto product : productInfo) {
            System.out.println("상품명: " + product.getName());
            System.out.println("가격: " + product.getPrice());
            System.out.println("수량: " + product.getQuantity());
        }
    }

    //총 주문 금액 계산 테스트
    @Test
    public void calculateTest() {
        List<String> productDetailIds = Arrays.asList("PD001", "PD002");
        List<Integer> quantities = Arrays.asList(2, 1);
        when(orderRepository.findProductInfoByProductDetailId(any())).thenReturn(mockProductInfo);

        List<ProductInfoDto> productInfo = orderService.getProductInfoByProductDetailId(productDetailIds, quantities);
        BigDecimal totalAmount = orderService.calculateTotalAmount(productInfo);

        assertEquals(new BigDecimal("50000"), totalAmount);

        for (ProductInfoDto product : productInfo) {
            System.out.println("상품명: " + product.getName());
            System.out.println("총가격: " + product.getTotalPrice());
        }
        System.out.println("총 주문 금액: " + totalAmount);
    }
}