package org.example.shoppingmall.controller.order;

import jakarta.servlet.http.HttpSession;
import org.example.shoppingmall.dto.User.UserInfoDto;
import org.example.shoppingmall.dto.order.*;
import org.example.shoppingmall.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

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
/*        UserInfoDto auth = (UserInfoDto) session.getAttribute("auth");
        if (auth != null) {
            String customerId = auth.getCustomerId(); // Customer ID 추출
            AddressDto address = orderService.getDefaultAddress(customerId);
            model.addAttribute("address", address);
        }*/

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

/*        // OrderProductsDto에서 productInfoDtos 가져오기
        ArrayList<ProductInfoDto> productInfoDtos = orderProductsDto.getProductInfoDtos();

        // productInfoDtos가 비어있지 않다면 처리
        if (productInfoDtos != null && !productInfoDtos.isEmpty()) {
            for (ProductInfoDto product : productInfoDtos) {
                // 각각의 상품 정보에서 productDetailId와 quantity를 추출
                productDetailId.add(product.getProductDetailId());
                quantity.add(product.getQuantity());
            }

            // 상품 정보 조회
            List<ProductInfoDto> productInfo = orderService.getProductInfoByProductDetailId(productDetailId, quantity);
            model.addAttribute("productInfo", productInfo);
        }
        System.out.println(orderProductsDto);
        return "order/orderForm";*/


    }

   /* @PostMapping("/order")
    public String submitOrder(@ModelAttribute List<ProductInfoDto> productInfoList, Model model) {
        for (ProductInfoDto productInfo : productInfoList) {
            System.out.println("상품명: " + productInfo.getName());
            System.out.println("수량: " + productInfo.getQuantity());
        }
        model.addAttribute("productInfoList", productInfoList);
        return "order/orderForm";
    }*/

   /* @PostMapping("/order")
    public String test(@ModelAttribute("productDetailInfo") ProductInfoDto productInfoDto) {
        System.out.println("productInfoDto = " + productInfoDto.getQuantity());
        System.out.println("productInfoDto = " + productInfoDto.getProductDetailId());
        return "order/orderForm";
    }*/




    /*@PostMapping("/order/submit")
    public String submitPayment(OrderDto orderDto) {
        orderService.createOrder(orderDto);
        return "redirect:/order/payment"; // 주문 완료 페이지로 리다이렉션
    }*/



    /*주문 목록 페이지
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
