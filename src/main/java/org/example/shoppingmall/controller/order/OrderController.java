package org.example.shoppingmall.controller.order;

import jakarta.servlet.http.HttpSession;
import org.example.shoppingmall.dto.order.*;
import org.example.shoppingmall.service.order.OrderListService;
import org.example.shoppingmall.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderListService orderListService;

    // 주문서 페이지
    @GetMapping("/order/order")
    public String showOrder(
                            @RequestParam("productDetailId") List<String> productDetailId,
                            @RequestParam("quantity") List<Integer> quantity,
                            HttpSession session, Model model) {

        String customerId = (String) session.getAttribute("customerId");
        model.addAttribute("customerId", customerId);
        System.out.println("customerId = " + customerId);

        //주문번호 표시
        Long showOrderId = orderService.getOrderId();
        model.addAttribute("showOrderId", showOrderId);

        //기본배송지 가져오기
        AddressDto address = orderService.getDefaultAddress(customerId);
        model.addAttribute("address", address);


        //주문상품정보 가져오기
        List<ProductInfoDto> productInfo =
                orderService.getProductInfoByProductDetailId(productDetailId, quantity);
        model.addAttribute("productInfo", productInfo);
        return "order/orderForm";

    }

    @PostMapping("/order/order")
    public String showOrder(@ModelAttribute("productInfo") ProductInfoDto productInfoDto,
                            HttpSession session, Model model) {

        String customerId = (String) session.getAttribute("customerId");
        model.addAttribute("customerId", customerId);
        System.out.println("customerId = " + customerId);

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

    //주문목록
    @GetMapping(value = "/order/list")
    public String showOrderList(HttpSession session, Model model) {
        String customerId = (String) session.getAttribute("customerId");
        model.addAttribute("customerId", customerId);

        if (customerId == null) {
            return "redirect:/user/login";
        }

        List<OrderListDto> orders = orderListService.getOrderListByCustomerId(customerId);

        // 주문번호별로 그룹화
        Map<Integer, List<OrderListDto>> groupedOrders = orders.stream()
                .collect(Collectors.groupingBy(OrderListDto::getOrderId));

        model.addAttribute("groupedOrders", groupedOrders);
        System.out.println("groupedOrders = " + groupedOrders);

        return "order/orderList";
    }

    //주문상세
    @RequestMapping("/order/detail")
    public String showOrderDetail(@RequestParam("orderId") Long orderId, Model model) {
        List<OrderDetailDto> orderDetails = orderListService.getOrderDetailByOrderId(orderId);

        //주문번호별로 그룹화
        Map<Long, List<OrderDetailDto>> groupedOrderDetails = orderDetails.stream()
                .collect(Collectors.groupingBy(OrderDetailDto::getOrderId));

        model.addAttribute("groupedOrderDetails", groupedOrderDetails);
        System.out.println("groupedOrderDetails = " + groupedOrderDetails);

        return "order/orderDetail";
    }

    //주문내역삭제

    @GetMapping("/order/delete/{orderId}")
    public String deleteOrder(@PathVariable Long orderId, HttpSession session, Model model) {

        String customerId = (String) session.getAttribute("customerId");
        if (customerId != null) {
            orderListService.deleteOrder(orderId, customerId);  // 서비스 호출
            model.addAttribute("message", "주문이 삭제되었습니다.");
        } else {
            model.addAttribute("message", "로그인이 필요합니다.");
        }
        return "redirect:/order/list";  // 주문 목록 페이지로 리디렉션
    }
}