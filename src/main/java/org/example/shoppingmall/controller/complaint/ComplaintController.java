package org.example.shoppingmall.controller.complaint;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;  // Model import 추가


@Controller
public class ComplaintController {
    @GetMapping("/complaint") //기본 민원 페이지
    public String complaint() {
        return "complaint/complaint.html";
    }



    @PostMapping("/complaintValue") //문의 내용, 문의 제목 넘겨주기
    public String complaintValue(@RequestParam("complaintTitle") String complaintTitle,
                                 @RequestParam("complaintText") String complaintText, Model model) {

        if (complaintText.equals("") || complaintTitle.equals("")) { // 문의 내용 혹은 제목을 입력안하면 알림메세지 띄워주기
            model.addAttribute("alertMessage", "제목과 내용을 입력해주세요.");
            return "complaint/complaint";
        }

        model.addAttribute("complaintTitle", complaintTitle);
        model.addAttribute("complaintText", complaintText);

        return "complaint/complaintValue.html";
    }
}
