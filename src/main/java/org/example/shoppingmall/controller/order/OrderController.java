package org.example.shoppingmall.controller.order;

import org.example.shoppingmall.dto.order.AddressDto;
import org.example.shoppingmall.dto.order.OrderDetailDto;
import org.example.shoppingmall.dto.order.OrderDto;
import org.example.shoppingmall.dto.order.ProductInfoDto;
import org.example.shoppingmall.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 주문서 페이지
    @GetMapping("/order")
    public String showOrder(@RequestParam("customerId") String customerId,
                            @RequestParam("productDetailId") String productDetailId,
                            Model model) {

        //기본배송지 가져오기
        AddressDto address = orderService.getDefaultAddress(customerId);
        model.addAttribute("address", address);

        ProductInfoDto productInfo = orderService.getProductInfoByProductDetailId(productDetailId);
        model.addAttribute("productInfo", productInfo);
        return "order/orderForm";
    }

    @PostMapping("/orderTest")
    public String test(@ModelAttribute("productDetailInfo") ProductInfoDto productInfoDto) {
        System.out.println("productInfoDto = " + productInfoDto.getQuantity());
        System.out.println("productInfoDto = " + productInfoDto.getProductDetailId());
        return "order/orderForm";
    }



   /* // 주문 생성 처리
    @PostMapping("/order/submit")
    public String submitPayment(OrderDto orderDto) {
        orderService.createOrder(orderDto);
        return "redirect:/order/payment"; // 주문 완료 페이지로 리다이렉션
    }

    // 주문 목록 페이지
    @GetMapping("/order/list")
    public String orderList(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orderList"; // 주문 목록 페이지
    }

    // 주문 상세 페이지
    @GetMapping("/order/detail/{orderId}")
    public String orderDetail(@PathVariable String orderId, Model model) {
        model.addAttribute("order", orderService.getOrderDetail(orderId));
        return "orderDetail"; // 주문 상세 페이지
    }*/
}
