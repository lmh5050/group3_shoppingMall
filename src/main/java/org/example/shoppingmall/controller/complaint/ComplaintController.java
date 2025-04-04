package org.example.shoppingmall.controller.complaint;

import jakarta.servlet.http.HttpSession;
import org.example.shoppingmall.dto.complaint.ComplaintDto;
import org.example.shoppingmall.dto.product.ProductCategoryDto;
import org.example.shoppingmall.service.complaint.ComplaintService;
import org.example.shoppingmall.service.product.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ComplaintController {

    private final ComplaintService complaintService;
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ComplaintController(ComplaintService complaintService, ProductCategoryService productCategoryService) {
        this.complaintService = complaintService;
        this.productCategoryService = productCategoryService;
    }

    // 기본 민원 리스트 페이지
    @GetMapping("/complaint/list")
    public String complaint(HttpSession session, Model model) {
        // 대분류 카테고리 가져오기
        ArrayList<ProductCategoryDto> list = productCategoryService.getMajorCategoryByPId();
        model.addAttribute("categoryList", list);

        //customerId별로 조회하기 위해 추가
        String customerId = (String) session.getAttribute("customerId");
        model.addAttribute("customerId", customerId);

        // 모든 complaint 목록 조회
//        List<ComplaintDto> complaints = complaintService.getAllComplaints();

        List<ComplaintDto> complaints = complaintService.getComplaintsByCustomerId(customerId);
        model.addAttribute("complaints", complaints); // complaints 데이터를 뷰로 전달
        return "complaint/complaintList";
    }

    //민원 신청 페이지(orderId 받도록 수정)
    @GetMapping("/complaint/{orderId}")
    public String complaintForm(@PathVariable("orderId") Long orderId, Model model) {
        //한 주문에서 여러 개 상품
        List<String> productName = complaintService.findProductNameByOrderId(orderId);

        model.addAttribute("orderId", orderId);
        model.addAttribute("productNames", productName);
        model.addAttribute("editMode", false);
        return "complaint/complaintForm";
    }

    //민원 신청
    @PostMapping("/complaint/request")
    public String requestComplaint( @RequestParam ("complaintType") String complaintType,
                                    @RequestParam String complaintTitle,
                                    @RequestParam String complaintText,
                                    @RequestParam String pickupAddress,
                                    @RequestParam Long orderId,
                                    @RequestParam String productName,
                                    RedirectAttributes redirectAttributes) {

        // 기존 민원이 있는지 확인, RedirectAttributes 는 페이지가 다시 로드되면 값 사라짐
        if (complaintService.isComplaintAlreadyExists(orderId, productName)) {
            redirectAttributes.addFlashAttribute("errorMessage", "이미 신청한 민원입니다.");
            return "redirect:/complaint/list"; // list 페이지로 리다이렉트
        }


        // ComplaintService에서 complaint 저장 처리
        complaintService.saveComplaint(complaintType, complaintTitle, complaintText, pickupAddress, orderId, productName);

        return "redirect:/complaint/list";
    }

    //complaint/list에서 수정버튼 누르면 기존에 저장되어 있던 정보가지고 Form화면으로 이동
    @GetMapping("/complaint/edit")
    public String editComplaint(@RequestParam("complaintId") String complaintId,
                                @RequestParam("orderId") Long orderId,
                                @RequestParam("productName") String productName,
                                Model model) {

        ComplaintDto complaint = complaintService.getComplaintById(complaintId);

        //orderId, productName 있을 시 값 전달
        model.addAttribute("complaint", complaint);
        model.addAttribute("orderId", orderId);
        model.addAttribute("productName", productName);
        model.addAttribute("editMode", true); // 수정 모드 표시

        return "complaint/complaintForm";
    }


    //수정버튼으로 이동 후 수정하고 나서 complaint/list로 이동
    @PostMapping("/complaint/update")
    public String updateComplaint(@RequestParam("complaintId") String complaintId,
                                  @RequestParam("complaintTitle") String complaintTitle,
                                  @RequestParam("complaintText") String complaintText,
                                  @RequestParam("pickupAddress") String pickupAddress) {

        // 수정된 데이터를 저장하는 서비스 호출
        complaintService.updateComplaint(complaintId, complaintTitle, complaintText, pickupAddress);

        return "redirect:/complaint/list"; // 수정 후 목록 페이지로 이동
    }

    // 민원 상세 페이지
    @GetMapping("/complaint/detail/{complaintId}")
    public String viewComplaintDetail(@PathVariable("complaintId") String complaintId, Model model) {
        // complaintId로 해당 민원 조회
        ComplaintDto complaint = complaintService.getComplaintById(complaintId);

        // 조회한 민원 데이터를 모델에 추가
        model.addAttribute("complaint", complaint);

        return "complaint/complaintDetail"; // 상세 페이지로 이동
    }

    //민원 삭제 플래그 활성화
    @PostMapping("/complaint/delete/{complaintId}")
    public String deleteComplaint(@PathVariable("complaintId") String complaintId,
                                  @RequestParam ("complaintType")String complaintType) {
        complaintService.getComplaintById(complaintId);

        complaintService.deleteComplaint(complaintId, complaintType);


        return "redirect:/complaint/list";
    }
}
