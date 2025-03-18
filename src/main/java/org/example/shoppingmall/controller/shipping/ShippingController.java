package org.example.shoppingmall.controller.shipping;


import org.example.shoppingmall.dto.shipping.ShippingCompanyDto;
import org.example.shoppingmall.service.Shipping.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class ShippingController{
    final private ShippingService shippingService;

    @Autowired
    public ShippingController(ShippingService shipp) {
        this.shippingService = shipp;
    }

    @GetMapping("/tracking")
    public String shippingTracking(){

        ArrayList<ShippingCompanyDto> dto = shippingService.shippingCompany();
        System.out.println(dto);
        for (ShippingCompanyDto dto1 : dto) {
            System.out.println(dto1);
        }
        return "shippingTracking";//url에 tracking 입력하면 index화면 띄움

    }
}