package org.example.shoppingmall.controller.payment;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.dto.payment.PaymentDto;
import org.example.shoppingmall.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping
    public List<?> getPayment() {
        return paymentService.getPayments();
    }

    @PostMapping
    public ResponseEntity<String> postPayment(@RequestBody PaymentDto paymentDto) {
        paymentService.createPayment(paymentDto);
        return ResponseEntity.ok("created payment");
    }
}
