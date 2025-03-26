package org.example.shoppingmall.controller.shipping;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.dto.CodeDetailDto;
import org.example.shoppingmall.dto.shipping.ShippingDto;
import org.example.shoppingmall.service.CodeDetailService;
import org.example.shoppingmall.service.Shipping.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
//@RequiredArgsConstructor

public class ShippingController {
    private final ShippingService shippingService;
    private final CodeDetailService codeDetailService;

    @Autowired
    public ShippingController(ShippingService shippingService, CodeDetailService codeDetailService) {
        this.shippingService = shippingService;
        this.codeDetailService = codeDetailService;
    }

    //배송리스트 전체 조회
    @GetMapping("/shipping/list")
    public String shippingList(Model model) {
        List<ShippingDto> shippingList = shippingService.getAllShipping();
        model.addAttribute("shippingList", shippingList);
        return "shipping/shippingList";
    }
    //배송 상세 페이지
    @GetMapping("/detail/{id}")
    public String detailShippingList(@PathVariable("id") String id, Model model) {
        ShippingDto shippingDto = shippingService.getDetailShipping(id);
        model.addAttribute("findDetailShippingId", shippingDto);
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
                                   @ModelAttribute ShippingDto shippingDto){
        shippingDto.setShippingId(id);
        shippingService.updateShippingListId(shippingDto);
        System.out.println("shippingDto = " + shippingDto);
        return "redirect:/detail/"+id;
    }
    // 랜덤 운송장 번호 생성 메서드
    private String generateRandomTrackingNumber() {
        //12자리 운송장 번호 랜덤 생성
        StringBuilder trackingNumber = new StringBuilder();

        // 첫 번째 자리 숫자는 1~9 사이에서 랜덤으로 선택
        int firstDigit = (int) (Math.random() * 9) + 1; // 1부터 9까지 랜덤
        trackingNumber.append(firstDigit);

        // 나머지 11자리 숫자는 0~9 사이에서 랜덤으로 선택
        for (int i = 1; i < 12; i++) {
            int digit = (int) (Math.random() * 10); // 0부터 9까지 랜덤
            trackingNumber.append(digit);
        }

        return trackingNumber.toString();
    }
    @PostMapping("/generate/tracking/no")
    @ResponseBody
    public String generateTrackingNumber() {
        return generateRandomTrackingNumber();  // 생성된 운송장 번호 반환
    }


    //배송 목록 삭제
    @PostMapping("/delete/{id}")
    public String deleteShippingId(@PathVariable("id") String id){
        shippingService.deleteShipping(id);
        return "redirect:/detail/"+id;
        }
//    @GetMapping("/shipping")
//    public String showShippingPage(Model model) {
//        // Shipping 객체에서 날짜 가져오기
//        LocalDateTime shippingDatetime = updateShipping.getShippingDatetime();
//
//        // 날짜 포맷팅
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//        String formattedDate = shippingDatetime.format(formatter);
//
//        model.addAttribute("formattedShippingDatetime", formattedDate);
//        return "shipping/updateShippingList";
   // }
    //    @PostMapping("/creat/tracking/no")
//    public String generateTrackingNumber(@ModelAttribute ShippingDto shippingDto, Model model) {
//        // 운송장 번호 생성 로직
//        String trackingNumber = generateRandomTrackingNumber();
//        shippingDto.setTrackingNumber(trackingNumber);
//
//        // 생성된 운송장 번호를 모델에 담아 view에 전달
//        model.addAttribute("updateShipping", shippingDto);
//        return "shipping/updateShippingList";  // 수정 페이지로 돌아가서 번호를 표시
//    }

}

