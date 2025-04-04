/*
package org.example.shoppingmall.service.order;

import org.example.shoppingmall.dto.order.OrderDetailDto;
import org.example.shoppingmall.dto.order.OrderDto;
import org.example.shoppingmall.dto.order.OrderListDto;
import org.example.shoppingmall.repository.order.OrderListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderListServiceTest {

    @InjectMocks
    private OrderListService orderListService;

    @Mock
    private OrderListRepository orderListRepository;

    private List<OrderDto> mockOrderAll;
    private List<OrderListDto> mockOrderList;
    private List<OrderDetailDto> mockOrderDetail;

    @BeforeEach
    void setUp() {
        // 전체 주문 목록(관리자용) 설정
        OrderDto order1 = new OrderDto();
        order1.setOrderId(2000L);
        order1.setCustomerId("customer1");
        order1.setTotalQuantity(2);
        order1.setFinalAmount(new BigDecimal("50000"));
        order1.setCreatedAt(Timestamp.valueOf("2024-01-01 10:00:00"));

        OrderDto order2 = new OrderDto();
        order2.setOrderId(2001L);
        order2.setCustomerId("customer2");
        order2.setTotalQuantity(1);
        order2.setFinalAmount(new BigDecimal("30000"));
        order2.setCreatedAt(Timestamp.valueOf("2024-01-02 11:00:00"));

        mockOrderAll = Arrays.asList(order1, order2);

        // 고객별 주문 목록 설정
        OrderListDto orderList1 = new OrderListDto();
        orderList1.setOrderId(2000L);
        orderList1.setOrderStatus("주문완료");
        orderList1.setProductName("테스트 상품1");
        orderList1.setQuantity(2);
        orderList1.setProductTotalPrice("40000");
        orderList1.setCreatedAt(Timestamp.valueOf("2024-01-01 10:00:00"));

        OrderListDto orderList2 = new OrderListDto();
        orderList2.setOrderId(2000L);
        orderList2.setOrderStatus("주문완료");
        orderList2.setProductName("테스트 상품2");
        orderList2.setQuantity(1);
        orderList2.setProductTotalPrice("10000");
        orderList2.setCreatedAt(Timestamp.valueOf("2024-01-01 10:00:00"));

        mockOrderList = Arrays.asList(orderList1, orderList2);

        // 주문 상세 정보 설정
        OrderDetailDto orderDetail1 = new OrderDetailDto();
        orderDetail1.setOrderId(2000L);
        orderDetail1.setCustomerId("customer1");
        orderDetail1.setTotalAmount(new BigDecimal("50000"));
        orderDetail1.setFinalAmount(new BigDecimal("50000"));
        orderDetail1.setProductName("테스트 상품1");
        orderDetail1.setQuantity(2);
        orderDetail1.setProductTotalPrice("40000");

        OrderDetailDto orderDetail2 = new OrderDetailDto();
        orderDetail2.setOrderId(2000L);
        orderDetail2.setCustomerId("customer1");
        orderDetail2.setTotalAmount(new BigDecimal("50000"));
        orderDetail2.setFinalAmount(new BigDecimal("50000"));
        orderDetail2.setProductName("테스트 상품2");
        orderDetail2.setQuantity(1);
        orderDetail2.setProductTotalPrice("10000");

        mockOrderDetail = Arrays.asList(orderDetail1, orderDetail2);
    }

    // 전체 주문 목록 조회 테스트(관리자용)
    @Test
    public void orderAllTest() {
        when(orderListRepository.selectOrderAll()).thenReturn(mockOrderAll);

        List<OrderDto> result = orderListService.getOrderAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        
        // 첫 번째 주문 검증
        assertEquals(2000L, result.get(0).getOrderId());
        assertEquals("customer1", result.get(0).getCustomerId());
        assertEquals(2, result.get(0).getTotalQuantity());
        assertEquals(new BigDecimal("50000"), result.get(0).getFinalAmount());

        // 두 번째 주문 검증
        assertEquals(2001L, result.get(1).getOrderId());
        assertEquals("customer2", result.get(1).getCustomerId());
        assertEquals(1, result.get(1).getTotalQuantity());
        assertEquals(new BigDecimal("30000"), result.get(1).getFinalAmount());

        for (OrderDto order : result) {
            System.out.println("주문번호: " + order.getOrderId());
            System.out.println("고객ID: " + order.getCustomerId());
            System.out.println("주문수량: " + order.getTotalQuantity());
            System.out.println("최종금액: " + order.getFinalAmount());
            System.out.println("주문일시: " + order.getCreatedAt());
        }
    }

    // 고객별 주문 목록 조회 테스트
    @Test
    public void orderListTest() {
        String customerId = "customer1";
        when(orderListRepository.findOrderListByCustomerId(customerId)).thenReturn(mockOrderList);

        List<OrderListDto> result = orderListService.getOrderListByCustomerId(customerId);

        assertNotNull(result);
        assertEquals(2, result.size());
        
        // 첫 번째 주문 검증
        assertEquals(2000L, result.get(0).getOrderId());
        assertEquals("주문완료", result.get(0).getOrderStatus());
        assertEquals("테스트 상품1", result.get(0).getProductName());
        assertEquals(2, result.get(0).getQuantity());
        assertEquals("40000", result.get(0).getProductTotalPrice());

        // 두 번째 주문 검증
        assertEquals(2000L, result.get(1).getOrderId());
        assertEquals("주문완료", result.get(1).getOrderStatus());
        assertEquals("테스트 상품2", result.get(1).getProductName());
        assertEquals(1, result.get(1).getQuantity());
        assertEquals("10000", result.get(1).getProductTotalPrice());

        // 콘솔 출력
        for (OrderListDto order : result) {
            System.out.println("주문번호: " + order.getOrderId());
            System.out.println("주문상태: " + order.getOrderStatus());
            System.out.println("상품명: " + order.getProductName());
            System.out.println("수량: " + order.getQuantity());
            System.out.println("상품총액: " + order.getProductTotalPrice());
            System.out.println("주문일시: " + order.getCreatedAt());
        }
    }

    // 주문 상세 조회 테스트
    @Test
    public void orderDetailTest() {
        Long orderId = 2000L;
        when(orderListRepository.findOrderDetailByOrderId(orderId)).thenReturn(mockOrderDetail);

        List<OrderDetailDto> result = orderListService.getOrderDetailByOrderId(orderId);

        assertNotNull(result);
        assertEquals(2, result.size());
        
        // 첫 번째 주문 상세 검증
        assertEquals(2000L, result.get(0).getOrderId());
        assertEquals("customer1", result.get(0).getCustomerId());
        assertEquals(new BigDecimal("50000"), result.get(0).getTotalAmount());
        assertEquals("테스트 상품1", result.get(0).getProductName());
        assertEquals(2, result.get(0).getQuantity());
        assertEquals("40000", result.get(0).getProductTotalPrice());

        // 두 번째 주문 상세 검증
        assertEquals(2000L, result.get(1).getOrderId());
        assertEquals("customer1", result.get(1).getCustomerId());
        assertEquals(new BigDecimal("50000"), result.get(1).getTotalAmount());
        assertEquals("테스트 상품2", result.get(1).getProductName());
        assertEquals(1, result.get(1).getQuantity());
        assertEquals("10000", result.get(1).getProductTotalPrice());

        for (OrderDetailDto orderDetail : result) {
            System.out.println("주문번호: " + orderDetail.getOrderId());
            System.out.println("고객ID: " + orderDetail.getCustomerId());
            System.out.println("상품명: " + orderDetail.getProductName());
            System.out.println("수량: " + orderDetail.getQuantity());
            System.out.println("상품총액: " + orderDetail.getProductTotalPrice());
        }
    }

    @Test
    @DisplayName("주문 삭제 성공 테스트")
    public void deleteOrderSuccessTest() {
        Long orderId = 2000L;
        String customerId = "customer1";
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("customerId", customerId);

        orderListService.deleteOrder(orderId, customerId);

        verify(orderListRepository, times(1)).deleteOrder(params);
        verify(orderListRepository, times(1)).deletePayment(orderId);
        verify(orderListRepository, times(1)).deleteShipping(orderId);

        System.out.println("=== 주문 삭제 성공 ===");
        System.out.println("주문번호: " + orderId);
        System.out.println("고객ID: " + customerId);
    }

    @Test
    @DisplayName("다른 고객의 주문 삭제 시도 테스트")
    public void deleteOrderFailTest() {
        Long orderId = 2000L;
        String wrongCustomerId = "wrongCustomer";
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("customerId", wrongCustomerId);

        orderListService.deleteOrder(orderId, wrongCustomerId);

        verify(orderListRepository, times(1)).deleteOrder(params);
        verify(orderListRepository, times(1)).deletePayment(orderId);
        verify(orderListRepository, times(1)).deleteShipping(orderId);

        System.out.println("=== 다른 고객의 주문 삭제 시도 ===");
        System.out.println("주문번호: " + orderId);
        System.out.println("잘못된 고객ID: " + wrongCustomerId);
    }

    @Test
    @DisplayName("존재하지 않는 주문 삭제 시도 테스트")
    public void deleteOrderFailTest2() {
        Long nonExistentOrderId = 9999L;
        String customerId = "customer1";
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", nonExistentOrderId);
        params.put("customerId", customerId);

        orderListService.deleteOrder(nonExistentOrderId, customerId);

        verify(orderListRepository, times(1)).deleteOrder(params);
        verify(orderListRepository, times(1)).deletePayment(nonExistentOrderId);
        verify(orderListRepository, times(1)).deleteShipping(nonExistentOrderId);

        System.out.println("=== 존재하지 않는 주문 삭제 시도 ===");
        System.out.println("존재하지 않는 주문번호: " + nonExistentOrderId);
        System.out.println("고객ID: " + customerId);
    }
}*/
