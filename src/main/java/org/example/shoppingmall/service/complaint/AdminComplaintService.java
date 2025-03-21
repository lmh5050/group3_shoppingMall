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

    public AdminComplaintService(AdminComplaintRepository adminComplaintRepository) {
        this.adminComplaintRepository = adminComplaintRepository;
    }

    public List<ComplaintDto> getCustomerComplaints() {return adminComplaintRepository.findCustomerComplaints();}

    // complaintId로 고객 민원 조회
    public ComplaintDto getCustomerComplaintById(String complaintId) {
        return adminComplaintRepository.findCustomerComplaintById(complaintId);
    }


    public void responseCustomerComplaint(String complaintId, String comment) {
        // 현재 시간을 request_datetime 으로 넣기 위해 LocalDateTime 사용
        LocalDateTime currentDateTime = LocalDateTime.now();

        ComplaintDto complaintDto = new ComplaintDto();
        complaintDto.setComplaintId(complaintId);
        complaintDto.setComment(comment);
        complaintDto.setReceivedDatetime(Timestamp.valueOf(currentDateTime));

        adminComplaintRepository.responseCustomerComplaint(complaintDto);
    }

    public void deleteCustomerComplaint(String complaintId) {
        ComplaintDto complaintDto = new ComplaintDto();
        complaintDto.setComplaintId(complaintId);

        adminComplaintRepository.deleteCustomerComplaint(complaintDto);
    }

}
