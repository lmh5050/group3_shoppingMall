package org.example.shoppingmall.controller.payment;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.dto.payment.PaymentDto;
import org.example.shoppingmall.dto.payment.PaymentInfoDto;
import org.example.shoppingmall.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PaymentController {
    private final PaymentService paymentService;

    // 결제 데이터 Get 테스트
    @GetMapping
    public List<?> getPaymentPage() {
        return paymentService.getPayments();
    }

    // 결제버튼이 보이는 페이지
    @GetMapping("/page/{orderId}")
    public String paymentPage(@PathVariable String orderId, Model model){
        PaymentInfoDto order = paymentService.getOrderDetails(orderId);
        model.addAttribute("order", order);
        return "payment/payment";
    }

    // 결제 팝업 페이지
    @GetMapping("/popup")
    public String paymentPopup(@RequestParam String orderId, Model model) {
        PaymentInfoDto order = paymentService.getOrderDetails(orderId);
        model.addAttribute("order", order);
        return "payment/payment-popup";
    }

    // 결제하기
    @PostMapping
    public ResponseEntity<String> postPayment(@RequestBody PaymentDto paymentDto) {
        paymentService.createPayment(paymentDto);
        return ResponseEntity.ok("created payment");
    }
}