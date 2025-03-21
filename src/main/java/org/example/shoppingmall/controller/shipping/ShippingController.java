package org.example.shoppingmall.controller.shipping;//package org.example.shoppingmall.controller.shipping;//package org.example.shoppingmall.controller.shipping;
//
//import lombok.RequiredArgsConstructor;
//import org.example.shoppingmall.dto.shipping.ShippingDto;
//import org.example.shoppingmall.service.Shipping.ShippingService;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequiredArgsConstructor
//
//public class ShippingController {
//    private final ShippingService shippingService;
//
//    @GetMapping("/shippingCompany")
//    public String shippingCompany() {
//        return "shipping/shippingCompany";
//    }
//    @PostMapping("/shippingCompany")
//    public void shippingCompany12(ShippingDto shippingDto){
//        shippingService.shippingCompany(shippingDto);
////  }
////}
//
//import org.example.shoppingmall.dto.shipping.ShippingDto;
//import org.example.shoppingmall.service.Shipping.ShippingService;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@Controller
//public class ShippingController{
//    private final ShippingService shippingService;
//
//    public ShippingController(ShippingService shippingService) {
//        this.shippingService = shippingService;
//    }
//
//    @GetMapping("/test")
//    public String shippingCompany() {
//      return "shipping/test";
//   }
//   @PostMapping("/test")
//    public void shippingCompany12(ShippingDto shippingDto) {
//       shippingService.test(shippingDto);
//   }
//}