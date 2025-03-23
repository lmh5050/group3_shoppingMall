package org.example.shoppingmall.service.complaint;

import org.springframework.stereotype.Service;

@Service
public class ComplaintStatusService {


    //민원 신청 시 타입따라 상태 값 설정
    public String applyComplaintStatusToRequest(String complaintType) {
        String status = "";

        if ("cancel".equals(complaintType)) {
            status = "MA03001";
        } else if ("refund".equals(complaintType)) {
            status = "MA05001";
        } else  {
            status = "MA04001";
        }
        return status;
    }

    //민원 삭제 시 타입따라 상태 값 설정
    public String applyComplaintStatusToDelete(String complaintType) {
        String status = "";

        if ("cancel".equals(complaintType)) {
            status = "MA03005";
        } else if ("refund".equals(complaintType)) {
            status = "MA05010";
        } else  {
            status = "MA04010";
        }
        return status;
    }

    //민원 답변 시 상태 값 설정
    public String applyComplaintStatusToResponse(String complaintResponseType, String complaintType) {
        String status = "";

        if ("resolved".equals(complaintResponseType) && "cancel".equals(complaintType)) {
            status = "MA03004";
        } else if ("rejected".equals(complaintResponseType) && "cancel".equals(complaintType)) {
            status = "MA03006";
        } else if ("resolved".equals(complaintResponseType) && "refund".equals(complaintType)) {
            status = "MA05008";
        } else if ("rejected".equals(complaintResponseType) && "refund".equals(complaintType)) {
            status = "MA05011";
        } else if ("resolved".equals(complaintResponseType) && "exchange".equals(complaintType)) {
            status = "MA04008";
        } else if ("rejected".equals(complaintResponseType) && "exchange".equals(complaintType)) {
            status = "MA04011";
        }

        return status;
    }
}
