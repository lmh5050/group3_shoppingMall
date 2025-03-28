package org.example.shoppingmall.controller.complaint;

import org.example.shoppingmall.dto.complaint.ComplaintDto;
import org.example.shoppingmall.service.complaint.AdminComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminComplaintController {

    private final AdminComplaintService adminComplaintService;

    @Autowired
    public AdminComplaintController(AdminComplaintService adminComplaintService) {
        this.adminComplaintService = adminComplaintService;
    }

    //관리자 민원 리스트 페이지
    @GetMapping("/admin/complaint/list")
    public String customerComplaintList(Model model) {
        List<ComplaintDto> complaints = adminComplaintService.getCustomerComplaints();
        model.addAttribute("complaints", complaints);
        return "complaint/adminComplaintList";
    }

    //고객 민원 답변 페이지 이동
    @GetMapping("/admin/complaint/{complaintId}")
    public String customerComplaintForm(@PathVariable("complaintId") String complaintId, Model model) {
        ComplaintDto complaint = adminComplaintService.getCustomerComplaintById(complaintId);
        model.addAttribute("complaint", complaint);
        return "complaint/adminComplaintForm";
    }

    // 민원 상세 페이지
    @GetMapping("/admin/complaint/detail/{complaintId}")
    public String viewComplaintDetail(@PathVariable("complaintId") String complaintId, Model model) {
        // complaintId로 해당 민원 조회
        ComplaintDto complaint = adminComplaintService.getCustomerComplaintById(complaintId);

        // 조회한 민원 데이터를 모델에 추가
        model.addAttribute("complaint", complaint);

        return "complaint/adminComplaintDetail"; // 상세 페이지로 이동
    }

    //고객 민원 접수
    @PostMapping("/admin/complaint/receive/{complaintId}")
    public String receivedCustomerComplaint(@PathVariable("complaintId") String complaintId,
                                            @RequestParam("complaintType") String complaintType) {

        adminComplaintService.receivedCustomerComplaint(complaintId, complaintType);

        return "redirect:/admin/complaint/list";
    }

    //고객 민원 답변
    @PostMapping("/admin/complaint/response/{complaintId}")
    public String responseCustomerComplaint(@PathVariable("complaintId") String complaintId,
                                            @RequestParam("comment") String comment,
                                            @RequestParam("complaintResponseType") String complaintResponseType,
                                            @RequestParam("complaintType") String complaintType,
                                            @RequestParam("orderId") Long orderId,
                                            @RequestParam("productName") String productName) {

        System.out.println(productName);
        adminComplaintService.responseCustomerComplaint(complaintId, comment, complaintResponseType, complaintType, orderId, productName);
        return "redirect:/admin/complaint/list";
    }
    //고객 민원 답변 수정 시 원래 내용 출력
    @GetMapping("/admin/complaint/edit/{complaintId}")
    public String editCustomerComplaint(@PathVariable("complaintId") String complaintId, Model model) {
        model.addAttribute("complaint", adminComplaintService.getCustomerComplaintById(complaintId));
        return "complaint/adminComplaintForm";
    }

    // 고객 민원 삭제
    @PostMapping("/admin/complaint/delete/{complaintId}")
    public String deleteCustomerComplaint(@PathVariable("complaintId") String complaintId) {
        adminComplaintService.getCustomerComplaintById(complaintId);

        adminComplaintService.deleteCustomerComplaint(complaintId);

        return "redirect:/admin/complaint/list";
    }
}
