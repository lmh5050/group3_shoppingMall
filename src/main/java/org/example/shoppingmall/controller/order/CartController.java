package org.example.shoppingmall.controller.order;

import jakarta.servlet.http.HttpSession;
import org.example.shoppingmall.dto.order.*;
import org.example.shoppingmall.service.order.CartService;
import org.example.shoppingmall.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public String showCart(@PathVariable String id, Model model , HttpSession session) {
        // 경로 변수 'id'를 사용하여 필요한 로직 처리
        String customerId = (String) session.getAttribute("customerId");

        // "order/cart" 페이지를 반환
        model.addAttribute("customerId", customerId);
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

    @PostMapping("/cart/cartOrder")
    @ResponseBody
    public void updateCartData(@RequestBody List<CartDto> CartInfo) {
        // customerId를 받아서 해당하는 Cart 데이터 반환
        cartService.updateCartData(CartInfo);
    }

    @PostMapping("/addToCart")
    @ResponseBody
    public ResponseEntity<String> insertCartData(@RequestBody CartDto cartInfo, HttpSession session) {
        try {
            String customerId = (String) session.getAttribute("customerId");

            // customerId가 null일 경우 예외 처리
            if (customerId == null) {
                throw new IllegalArgumentException("로그인 정보가 없습니다.");
            }

            // customerId를 설정하고 Cart 데이터 추가
            cartInfo.setCustomerId(customerId);
            cartService.insertCartData(cartInfo);

            // 성공적으로 처리되었을 경우 "success" 반환
            return ResponseEntity.ok("success");

        } catch (IllegalArgumentException e) {
            // 예외 처리: 클라이언트에게 오류 메시지 반환
            e.printStackTrace();

            // 예외 발생 시 "fail"을 반환하고, HTTP 상태 코드 400(Bad Request)로 설정
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
        }
    }

}
