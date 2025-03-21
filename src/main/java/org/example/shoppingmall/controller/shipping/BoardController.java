package org.example.shoppingmall.controller.shipping;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.dto.shipping.ShippingDto;
import org.example.shoppingmall.service.Shipping.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//
@Controller
public class BoardController {


    private final ShippingService shippingService;

    @Autowired
    public BoardController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @GetMapping("/test")
    public String test() {
        return "shipping/test";
    }

    @PostMapping("/test")
    public String test(ShippingDto shippingDto ) {
        shippingService.test(shippingDto);
        return "shipping/index";
    }
}
