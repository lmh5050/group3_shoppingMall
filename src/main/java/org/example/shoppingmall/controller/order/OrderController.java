package org.example.shoppingmall.controller.order;

import jakarta.servlet.http.HttpSession;
import org.example.shoppingmall.dto.order.*;
import org.example.shoppingmall.service.order.OrderListService;
import org.example.shoppingmall.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderListService orderListService;

    // 주문서 페이지
    @GetMapping("/order")
    public String showOrder(@RequestParam("customerId") String customerId,
                            @RequestParam("productDetailId") List<String> productDetailId,
                            @RequestParam("quantity") List<Integer> quantity,
                            Model model) {

        //기본배송지 가져오기
        AddressDto address = orderService.getDefaultAddress(customerId);
        model.addAttribute("address", address);


        //주문상품정보 가져오기
        List<ProductInfoDto> productInfo = orderService.getProductInfoByProductDetailId(productDetailId, quantity);
        model.addAttribute("productInfo", productInfo);
        return "order/orderForm";

    }

    @PostMapping("/order")
    public String test(@ModelAttribute("productInfo") ProductInfoDto productInfoDto,
                       HttpSession session, Model model) {

        //주문번호 표시
        Long showOrderId = orderService.getOrderId();
        model.addAttribute("showOrderId", showOrderId);

        //상품정보 표시
        List<String> productDetailId = new ArrayList<>();
        List<Integer> quantity = new ArrayList<>();

        productDetailId.add(productInfoDto.getProductDetailId());
        quantity.add(productInfoDto.getQuantity());
        List<ProductInfoDto> productInfo =
                orderService.getProductInfoByProductDetailId(
                        productDetailId,
                        quantity);
        model.addAttribute("productInfo", productInfo);


        return "order/orderForm";
    }

    //주문목록
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String showOrderList(@RequestParam(value = "customerId", required = false) String customerId,
                                Model model) {
        if (customerId == null) {
            return "user/login";
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
    @RequestMapping("/detail")
    public String showOrderDetail(@RequestParam("orderId") Long orderId, Model model) {
        List<OrderDetailDto> orderDetails = orderListService.getOrderDetailByOrderId(orderId);

        Map<Long, List<OrderDetailDto>> groupedOrderDetails = orderDetails.stream()
                .collect(Collectors.groupingBy(OrderDetailDto::getOrderId));

        model.addAttribute("groupedOrderDetails", groupedOrderDetails);
        System.out.println("groupedOrderDetails = " + groupedOrderDetails);
        return "order/orderDetail";


    }
}