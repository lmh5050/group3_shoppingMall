package org.example.shoppingmall.controller.complaint;

import org.example.shoppingmall.dto.complaint.ComplaintDto;
import org.example.shoppingmall.service.complaint.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class ComplaintController {

    private final ComplaintService complaintService;

    @Autowired
    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @GetMapping("/complaint/list") // 기본 민원 리스트 페이지
    public String complaint(Model model) {
        // 모든 complaint 목록 조회
        List<ComplaintDto> complaints = complaintService.getAllComplaints();
        model.addAttribute("complaints", complaints); // complaints 데이터를 뷰로 전달
        return "complaint/complaintList";
    }

    @GetMapping("/complaint") //민원 신청 페이지
    public String complaintForm() {
        return "complaint/complaintForm";
    }


    @PostMapping("/complaint/request")
    public String requestComplaint( @RequestParam ("complaintType") String complaintType,
                                    @RequestParam String complaintTitle,
                                    @RequestParam String complaintText) {

        // ComplaintService에서 complaint 저장 처리
        complaintService.saveComplaint(complaintType, complaintTitle, complaintText);

        return "redirect:/complaint/list";
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
}