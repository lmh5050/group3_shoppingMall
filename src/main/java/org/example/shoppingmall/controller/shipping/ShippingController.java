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

public class ShippingController {
private final ShippingService shippingService;
private final CodeDetailService codeDetailService;

    @Autowired
    public ShippingController(ShippingService shippingService, CodeDetailService codeDetailService) {
        this.shippingService = shippingService;
        this.codeDetailService = codeDetailService;
    }

    //배송리스트 전체 조회
    @GetMapping("/admin/shipping/list")
    //배송 서비스에서 모든 배송 정보 가져옴
    public String shippingList(Model model) {
        List<ShippingDto> shippingList = shippingService.getAllShipping();
        model.addAttribute("shippingList", shippingList);
        return "shipping/shippingList";
    }
    //배송 상세 페이지 조회
    @GetMapping("/detail/{id}")
    //배송 아이디를 통해 배송의 상세 정보 가져오기
    public String detailShippingList(@PathVariable("id") String id, Model model) {
        ShippingDto shippingDto = shippingService.getDetailShipping(id);
        model.addAttribute("shipping", shippingDto);
        return "shipping/detailShipping";
    }
    //배송 상세 페이지 수정
    @GetMapping("/update/{id}")
    //배송 아이디를 통해 배송 정보 가져오고 수정하기
    public String updateId(@PathVariable("id") String id, Model model){
        ShippingDto shippingDto = shippingService.updateShippingId(id);
        model.addAttribute("updateShipping", shippingDto);
        //코드 상세 테이블 중분류 6번(배송 지연 사유 관련) 가져오기
        ArrayList<CodeDetailDto> codeDetail = codeDetailService.getCodeDataByCodeCategory("6");
        model.addAttribute("codeDetail", codeDetail);
        return "shipping/updateShippingList";
    }
    //배송 상세 페이지 수정 후 저장
    @PostMapping("/update/{id}")
    //배송 아이디와 배송 회사 정보 가져오기
    public String updateShippingId(@PathVariable("id") String id,
                                   @ModelAttribute ShippingDto shippingDto,
                                   @RequestParam("deliveryCompany") String deliveryCompany){
        shippingDto.setShippingId(id);
        shippingDto.setShippingCompanyId(deliveryCompany);
        shippingService.updateShippingListId(shippingDto);
        return "redirect:/detail/"+id;
    }

    //고객 배송 조회 페이지
    @GetMapping("shipping/track")
    //주문아이디로 배송 정보 조회
    public String trackShipping(@RequestParam(name = "orderId") String orderId, Model model) {
        ShippingDto shippingDto = shippingService.getShippingListTrack(orderId);
        //배송 정보가 없으면 빈 객체 생성(빈 화면 보여주기)
        if (shippingDto == null) {
            shippingDto = new ShippingDto();
        }
        model.addAttribute("getShippingTrack", shippingDto);
        return "shipping/shippingTrack";
    }
}

