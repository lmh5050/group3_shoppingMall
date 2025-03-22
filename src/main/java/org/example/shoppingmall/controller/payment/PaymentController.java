package org.example.shoppingmall.controller.payment;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.dto.payment.PaymentInfoDto;
import org.example.shoppingmall.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PaymentController {
    private final PaymentService paymentService;

    // 결제버튼이 보이는 페이지
    @GetMapping("/page")
    public String paymentPage(Model model){
        model.addAttribute("orderId", 10001);
        model.addAttribute("totalOrderAmount", 30000);
        return "payment/payment";
    }

    // 결제 팝업 페이지
    @GetMapping
    public String paymentPopup() {
        return "payment/payment-popup";
    }

    // 결제하기
    @PostMapping
    public ResponseEntity<String> postPayment(@RequestBody PaymentInfoDto paymentInfoDto) {
        paymentService.processPayment(paymentInfoDto);
        return ResponseEntity.ok("결제가 완료되었습니다.");
    }

    // 영수증 조회 팝업
    @GetMapping("/receipt/{orderId}")
    public String viewReceipt(@PathVariable Integer orderId, Model model) {
        PaymentInfoDto receipt = paymentService.getReceipt(orderId);
        model.addAttribute("receipt", receipt);
        return "payment/receipt";
    }
}