package org.example.shoppingmall.controller.payment;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.dto.payment.PaymentPendingDto;
import org.example.shoppingmall.service.payment.PaymentAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/payment")
@RequiredArgsConstructor
public class PaymentAdminController {

    private final PaymentAdminService paymentAdminService;

    // 결제 대기건 관리 페이지
    @GetMapping
    public String getPendingPayments(Model model) {
        List<PaymentPendingDto> pendingPayments = paymentAdminService.getPendingPayments();
        model.addAttribute("pendingPayments", pendingPayments);
        return "payment/payment-admin";
    }

    // 결제 상태 변경
    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updatePaymentStatus(@RequestBody PaymentPendingDto paymentPendingDto) {
        paymentAdminService.updatePaymentStatus(paymentPendingDto);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "결제 상태가 변경되었습니다.");
        return ResponseEntity.ok(response);
    }
} 