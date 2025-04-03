package org.example.shoppingmall.controller.payment;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.dto.payment.PaymentInfoDto;
import org.example.shoppingmall.dto.payment.ReceiptDto;
import org.example.shoppingmall.service.payment.PaymentService;
import org.example.shoppingmall.service.payment.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PaymentController {
    private final PaymentService paymentService;
    private final ReceiptService receiptService;

    // 결제 팝업 페이지
    @GetMapping
    public String paymentPopup() {
        return "payment/payment-popup";
    }

    // 결제하기
    @PostMapping
    public ResponseEntity<String> postPayment(@RequestBody PaymentInfoDto paymentInfoDto) {
        try {
            paymentService.processPayment(paymentInfoDto);
            return ResponseEntity.ok("결제가 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("결제 처리 중 오류 발생" + e.getMessage());
        }
    }

    // 거래명세서 조회 팝업
    @GetMapping("/receipt/{orderId}")
    public String viewReceipt(@PathVariable Integer orderId, Model model) {
        ReceiptDto receipt = receiptService.getReceipt(orderId);
        model.addAttribute("order", receipt);
        return "payment/receipt";
    }

    // 테스트용 결제버튼이 보이는 페이지
//    @GetMapping("/page")
//    public String paymentPage(Model model){
//        model.addAttribute("orderId", 100026);
//        model.addAttribute("totalOrderAmount", 30000);
//        return "payment/payment";
//    }
}