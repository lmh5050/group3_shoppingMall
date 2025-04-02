package org.example.shoppingmall.controller.order;

import jakarta.servlet.http.HttpSession;
import org.example.shoppingmall.dto.order.*;
import org.example.shoppingmall.dto.product.ProductCategoryDto;
import org.example.shoppingmall.service.order.OrderListService;
import org.example.shoppingmall.service.order.OrderService;
import org.example.shoppingmall.service.product.ProductCategoryService;
import org.example.shoppingmall.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderListService orderListService;

    // 주문서 (장바구니)
    @GetMapping("/order")
    public String showOrder(@RequestParam("productDetailId") List<String> productDetailId,
                            @RequestParam("quantity") List<Integer> quantity,
                            HttpSession session, Model model) {

        String customerId = (String) session.getAttribute("customerId");
        model.addAttribute("customerId", customerId);

        //주문번호 표시
        Long showOrderId = orderService.getOrderId();
        model.addAttribute("showOrderId", showOrderId);

        //기본배송지 가져오기
        AddressDto address = orderService.getDefaultAddress(customerId);
        model.addAttribute("address", address);

        //주문상품정보 가져오기
        List<ProductInfoDto> productInfo =
                orderService.getProductInfoByProductDetailId(productDetailId, quantity);

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (ProductInfoDto product : productInfo) {
            totalAmount = totalAmount.add(product.getTotalPrice());
        }

        model.addAttribute("productInfo", productInfo);
        model.addAttribute("totalAmount", totalAmount);
        return "order/orderForm";

    }

    //주문서 (상품페이지)
    @PostMapping("/order")
    public String showOrder(@ModelAttribute("productInfo") ProductInfoDto productInfoDto,
                            HttpSession session, Model model) {

        String customerId = (String) session.getAttribute("customerId");
        model.addAttribute("customerId", customerId);

        if (customerId == null) {
            return "redirect:/user/login";
        }

        //주문번호 표시
        Long showOrderId = orderService.getOrderId();
        model.addAttribute("showOrderId", showOrderId);

        //기본배송지 가져오기
        AddressDto address = orderService.getDefaultAddress(customerId);
        model.addAttribute("address", address);

        //상품정보 표시
        List<String> productDetailId = new ArrayList<>();
        List<Integer> quantity = new ArrayList<>();

        productDetailId.add(productInfoDto.getProductDetailId());
        quantity.add(productInfoDto.getQuantity());
        List<ProductInfoDto> productInfo =
                orderService.getProductInfoByProductDetailId(
                        productDetailId,
                        quantity);

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (ProductInfoDto product : productInfo) {
            totalAmount = totalAmount.add(product.getTotalPrice());
        }

        model.addAttribute("productInfo", productInfo);
        model.addAttribute("totalAmount", totalAmount);
        return "order/orderForm";
    }

    // 주문목록
    @GetMapping(value = "/list")
    public String showOrderList(HttpSession session, Model model) {

        String customerId = (String) session.getAttribute("customerId");
        model.addAttribute("customerId", customerId);

        if (customerId == null) {
            return "redirect:/user/login";
        }

        List<OrderListDto> orders = orderListService.getOrderListByCustomerId(customerId);

        // 주문번호별로 그룹화
        Map<Long, List<OrderListDto>> groupedOrders = orders.stream()
                .collect(Collectors.groupingBy(OrderListDto::getOrderId));

        //자동 정렬 (내림차순)
        Map<Long, List<OrderListDto>> sortedGroupedOrders = new TreeMap<>(
                (orderId1, orderId2) -> Long.compare(orderId2, orderId1)
        );

        sortedGroupedOrders.putAll(groupedOrders);
        model.addAttribute("groupedOrders", sortedGroupedOrders);

        // 대분류 카테고리 가져오기
        ArrayList<ProductCategoryDto> list = productCategoryService.getMajorCategoryByPId();
        model.addAttribute("categoryList", list);

        return "order/orderList";
    }

    //주문상세
    @GetMapping("/detail/{orderId}")
    public String showOrderDetail(@PathVariable Long orderId, HttpSession session, Model model) {
        String customerId = (String) session.getAttribute("customerId");
        model.addAttribute("customerId", customerId);

        if (customerId == null) {
            return "redirect:/user/login";
        }
        System.out.println("customerId = " + customerId);
        System.out.println("orderId = " + orderId);

        // 주문 상세 정보 가져오기
        List<OrderDetailDto> orderDetails = orderListService.getOrderDetailByOrderId(orderId);
        System.out.println("orderDetails = " + orderDetails);

        // 주문이 존재하지 않거나, 고객 ID가 일치하지 않는 경우
        String orderCustomerId = orderDetails.stream()
                .findFirst()
                .map(OrderDetailDto::getCustomerId)
                .orElse(null);

        if (orderCustomerId == null || !customerId.equals(orderCustomerId)) {
            return "redirect:/user/login";
        }

        // 주문번호별로 그룹화
        Map<Long, List<OrderDetailDto>> groupedOrderDetails = orderDetails.stream()
                .collect(Collectors.groupingBy(OrderDetailDto::getOrderId));

        model.addAttribute("groupedOrderDetails", groupedOrderDetails);
        System.out.println("groupedOrderDetails = " + groupedOrderDetails);

        return "order/orderDetail";
    }
    /*@GetMapping("/detail/{orderId}")
    public String showOrderDetail(@PathVariable Long orderId, HttpSession session, Model model) {

        String customerId = (String) session.getAttribute("customerId");
        model.addAttribute("customerId", customerId);

        if (customerId == null) {
            return "redirect:/user/login";
        }

        // 주문 상세 정보 가져오기
        List<OrderDetailDto> orderDetails = orderListService.getOrderDetailByOrderId(orderId);


        if (orderDetails.isEmpty() || !customerId.equals(orderDetails.getFirst().getCustomerId())) {
            return "redirect:/user/login";
        }

        //주문번호별로 그룹화
        Map<Long, List<OrderDetailDto>> groupedOrderDetails = orderDetails.stream()
                .collect(Collectors.groupingBy(OrderDetailDto::getOrderId));

        model.addAttribute("groupedOrderDetails", groupedOrderDetails);
        System.out.println("groupedOrderDetails = " + groupedOrderDetails);

        return "order/orderDetail";
    }*/

    //주문내역삭제
    @GetMapping("/delete/{orderId}")
    public String deleteOrder(@PathVariable Long orderId, HttpSession session, Model model) {

        String customerId = (String) session.getAttribute("customerId");
        if (customerId != null) {
            orderListService.deleteOrder(orderId, customerId);  // 서비스 호출
        } else {
            return "redirect:/user/login";
        }
        return "redirect:/order/list";  // 주문 목록 페이지로 리디렉션
    }
}