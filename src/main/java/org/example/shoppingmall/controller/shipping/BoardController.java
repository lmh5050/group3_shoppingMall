package org.example.shoppingmall.controller.shipping;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.dto.complaint.ComplaintDto;
import org.example.shoppingmall.dto.shipping.ShippingDto;
import org.example.shoppingmall.service.Shipping.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

//
@Controller
public class BoardController {

    private final ShippingService shippingService;

    @Autowired
    public BoardController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

//홈 화면
    @GetMapping("/index")
    public String index() {
        return "shipping/index";
    }

//글 작성화면
    @GetMapping("/test")
    public String getTest() {
        return "shipping/test";
    }

//글 등록
    @PostMapping("/test")
    public String postTest(ShippingDto shippingDto ) {
        shippingService.test(shippingDto);
        return "redirect:/list";
    }

    //글 리스트 보기
    @GetMapping("/list")
    public String findAll(Model model){
        List<ShippingDto> shippingDto = shippingService.findAll();
        model.addAttribute("testList", shippingDto);
        return "shipping/list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model){
        ShippingDto shippingDto = shippingService.findById(id);
        model.addAttribute ("detailList", shippingDto);
        return "shipping/detail";

    }
}
