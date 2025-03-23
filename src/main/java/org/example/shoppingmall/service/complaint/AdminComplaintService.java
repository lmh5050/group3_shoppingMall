package org.example.shoppingmall.service.complaint;

import org.example.shoppingmall.dto.complaint.ComplaintDto;
import org.example.shoppingmall.repository.complaint.AdminComplaintRepository;
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


    //고객 민원 답변
    public void responseCustomerComplaint(String complaintId, String comment, String complaintResponseType, String complaintType) {
        // 현재 시간을 request_datetime 으로 넣기 위해 LocalDateTime 사용
        LocalDateTime currentDateTime = LocalDateTime.now();

        String status = complaintStatusService.applyComplaintStatusToResponse(complaintResponseType, complaintType);

        ComplaintDto complaintDto = new ComplaintDto();
        complaintDto.setComplaintId(complaintId);
        complaintDto.setComment(comment);
        complaintDto.setStatus(status);
        complaintDto.setEndDatetime(Timestamp.valueOf(currentDateTime));

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
