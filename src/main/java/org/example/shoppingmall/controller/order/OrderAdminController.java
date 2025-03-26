package org.example.shoppingmall.controller.order;

import org.example.shoppingmall.dto.order.OrderDto;
import org.example.shoppingmall.service.order.OrderListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class OrderAdminController {

    @Autowired
    private OrderListService orderListService;

    @GetMapping("/order")
    public String showOrderAll(Model model) {
        List<OrderDto> orderAll = orderListService.getOrderAll();
        model.addAttribute("orderAll", orderAll);
        return "order/orderAdmin";
    }

}
