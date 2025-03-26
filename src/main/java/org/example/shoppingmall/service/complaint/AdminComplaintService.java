package org.example.shoppingmall.service.complaint;

import org.example.shoppingmall.dto.complaint.ComplaintDto;
import org.example.shoppingmall.repository.complaint.AdminComplaintRepository;
import org.example.shoppingmall.repository.complaint.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminComplaintService {

    @Autowired
    private AdminComplaintRepository adminComplaintRepository;

    @Autowired
    private ComplaintStatusService complaintStatusService;

    public AdminComplaintService(AdminComplaintRepository adminComplaintRepository, ComplaintStatusService complaintStatusService) {
        this.adminComplaintRepository = adminComplaintRepository;
        this.complaintStatusService = complaintStatusService;
    }

    public List<ComplaintDto> getCustomerComplaints() {return adminComplaintRepository.findCustomerComplaints();}

    // complaintId로 고객 민원 조회
    public ComplaintDto getCustomerComplaintById(String complaintId) {
        return adminComplaintRepository.findCustomerComplaintById(complaintId);
    }

    // 고객 민원 접수
    public void receivedCustomerComplaint(String complaintId, String complaintType) {
        String status = complaintStatusService.applyComplaintStatusToReceived(complaintType);

        LocalDateTime currentDateTime = LocalDateTime.now();

        ComplaintDto complaintDto = new ComplaintDto();
        complaintDto.setComplaintId(complaintId);
        complaintDto.setStatus(status);
        complaintDto.setReceivedDatetime(Timestamp.valueOf(currentDateTime));

        adminComplaintRepository.receivedCustomerComplaint(complaintDto);
    }

    //고객 민원 답변
    public void responseCustomerComplaint(String complaintId, String comment, String complaintResponseType, String complaintType, Long orderId) {
        // 현재 시간을 request_datetime 으로 넣기 위해 LocalDateTime 사용
        LocalDateTime currentDateTime = LocalDateTime.now();

        String status = complaintStatusService.applyComplaintStatusToResponse(complaintResponseType, complaintType);

        // complaintType에 따라 refundAmount 처리
        double refundAmount = 0.0;  // 기본값은 0으로 설정

        if ("resolved".equals(complaintResponseType)) {
            // 'resolved' 상태에서만 환불 금액을 설정
            if ("cancel".equals(complaintType) || "refund".equals(complaintType)) {
                // 'cancel' 또는 'refund'인 경우에만 상품 총 금액을 예상 환불 금액으로 설정
                refundAmount = Double.parseDouble(adminComplaintRepository.findProductTotalPriceByOrderId(orderId));
            } else if ("exchange".equals(complaintType)) {
                // 'exchange'인 경우에는 환불 금액을 0으로 설정
                refundAmount = 0.0;
            }
        } else if ("rejected".equals(complaintResponseType)) {
            // 'rejected'인 경우에는 환불 금액을 0으로 설정
            refundAmount = 0.0;
        }

        ComplaintDto complaintDto = new ComplaintDto();
        complaintDto.setComplaintId(complaintId);
        complaintDto.setComment(comment);
        complaintDto.setStatus(status);
        complaintDto.setEndDatetime(Timestamp.valueOf(currentDateTime));
        complaintDto.setRefundAmount(refundAmount);

        adminComplaintRepository.responseCustomerComplaint(complaintDto);
    }

    //고객 민원 삭제 플래그 활성화
    public void deleteCustomerComplaint(String complaintId) {
        //deleteFlag 활성화 변수
        Byte onDeleteFlag = 1;

        ComplaintDto complaintDto = new ComplaintDto();
        complaintDto.setComplaintId(complaintId);
        complaintDto.setDeleteFlag(onDeleteFlag);

        adminComplaintRepository.deleteCustomerComplaint(complaintDto);
    }

}
