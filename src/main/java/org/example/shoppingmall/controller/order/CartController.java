package org.example.shoppingmall.controller.order;

import org.example.shoppingmall.dto.order.*;
import org.example.shoppingmall.service.order.CartService;
import org.example.shoppingmall.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/order")
public class CartController {
    private final CartService cartService ;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @GetMapping("/cart/{id}")
    public String showCart(@PathVariable String id, Model model) {
        // 경로 변수 'id'를 사용하여 필요한 로직 처리
        System.out.println("customerId: " + id); // 콘솔에 출력하거나 로직에 활용
        id = "admin";
        // "order/cart" 페이지를 반환
        model.addAttribute("customerId", id);
        // "order/cart" 페이지를 반환
        return "order/cart";
    }

    @GetMapping("/cart/data")
    @ResponseBody
    public List<CartDto> getCartData(@RequestParam String customerId) {
        // customerId를 받아서 해당하는 Cart 데이터 반환
        System.out.println("customerId: " + customerId);
        List<CartDto> cartList = cartService.getCartList(customerId);
        return cartList;
    }

    @PostMapping("/cart/order")
    @ResponseBody
    public List<CartDto> postOrderData(@RequestParam String customerId) {
        // customerId를 받아서 해당하는 Cart 데이터 반환
        System.out.println("customerId: " + customerId);
        List<CartDto> cartList = cartService.getCartList(customerId);
        return cartList;
    }
}
