package org.example.shoppingmall.controller.shipping;

import org.example.shoppingmall.dto.CodeDetailDto;
import org.example.shoppingmall.dto.shipping.ShippingDto;
import org.example.shoppingmall.service.CodeDetailService;
//import org.example.shoppingmall.service.Shipping.ShippingService;
import org.example.shoppingmall.service.Shipping.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
//@RequiredArgsConstructor
public class ShippingController {
    @Autowired
    private final ShippingService shippingService;
    private final CodeDetailService codeDetailService;

    @Autowired
    public ShippingController(ShippingService shippingService, CodeDetailService codeDetailService) {
        this.shippingService = shippingService;
        this.codeDetailService = codeDetailService;
    }

    //배송리스트 전체 조회
    @GetMapping("/admin/shipping/list")
    public String shippingList(Model model) {
        List<ShippingDto> shippingList = shippingService.getAllShipping();
        model.addAttribute("shippingList", shippingList);
        return "shipping/shippingList";
    }
    //배송 상세 페이지
    @GetMapping("/detail/{id}")
    public String detailShippingList(@PathVariable("id") String id, Model model) {
        ShippingDto shippingDto = shippingService.getDetailShipping(id);
        model.addAttribute("shipping", shippingDto);
        return "shipping/detailShipping";
    }
    //배송 상세 페이지 수정
    @GetMapping("/update/{id}")
    public String updateId(@PathVariable("id") String id, Model model){
        ShippingDto shippingDto = shippingService.updateShippingId(id);
        model.addAttribute("updateShipping", shippingDto);
        ArrayList<CodeDetailDto> codeDetail = codeDetailService.getCodeDataByCodeCategory("6");
        model.addAttribute("codeDetail", codeDetail);
        return "shipping/updateShippingList";
    }
    //배송 상세 페이지 수정 후 저장
    @PostMapping("/update/{id}")
    public String updateShippingId(@PathVariable("id") String id,
                                   @ModelAttribute ShippingDto shippingDto,
                                   @RequestParam("deliveryCompany") String deliveryCompany){
        shippingDto.setShippingId(id);
        shippingDto.setShippingCompanyId(deliveryCompany);
        shippingService.updateShippingListId(shippingDto);
        System.out.println(shippingDto.getShippingCompanyId());
        return "redirect:/detail/"+id;
    }

    //고객 배송 조회 페이지
    @GetMapping("shipping/track")
    public String trackShipping(@RequestParam(name = "orderId") String orderId, Model model) {
        ShippingDto shippingDto = shippingService.getShippingListTrack(orderId);
        if (shippingDto == null) {
            shippingDto = new ShippingDto();
        }
        model.addAttribute("getShippingTrack", shippingDto);
        return "shipping/shippingTrack";
    }
    //배송 목록 삭제
    @PostMapping("/delete/{id}")
    public String deleteShippingId(@PathVariable("id") String id){
        shippingService.deleteShipping(id);
        return "redirect:/detail/"+id;
    }

    //배송 이력 리스트 페이지 /Shipping/order?id=SHP003
    @GetMapping("/shipping/order")
    public String getShipping(@RequestParam(name = "id") String id, Model model) {
        ArrayList<ShippingDto> shippingDto = shippingService.getShippingDetailList(id);
        model.addAttribute("getShippingDetail", shippingDto);
        return "shipping/shippingHistory";
    }
//
//    //고객 배송 조회 페이지 -고객
//    @GetMapping("/Shipping/track")
//    public String trackShipping(@RequestParam(name = "orderId") String orderId, Model model) {
//        ArrayList<ShippingDto> shippingDto = shippingService.getShippingListTrack(orderId);
//        model.addAttribute("getShippingTrack", shippingDto);
//        System.out.println("shippingDto = " + shippingDto);
//        return "Shipping/shippingTrack";
//    }

}

