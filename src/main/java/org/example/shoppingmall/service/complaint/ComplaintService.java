package org.example.shoppingmall.service.complaint;

import org.example.shoppingmall.dto.complaint.ComplaintDto;
import org.example.shoppingmall.repository.complaint.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    // 모든 complaint 목록 조회
    public List<ComplaintDto> getAllComplaints() {
        return complaintRepository.findAllComplaints();
    }



    public void saveComplaint(String complaintType, String complaintTitle, String complaintText) {
        // UUID를 생성하여 complaint_id로 사용
        String complaintId = UUID.randomUUID().toString();

        // 현재 시간을 request_datetime에 넣기 위해 LocalDateTime 사용
        LocalDateTime currentDateTime = LocalDateTime.now();

        // ComplaintDto 객체에 값 설정
        ComplaintDto complaintDto = new ComplaintDto();
        complaintDto.setComplaintId(complaintId);
        complaintDto.setComplaintTypeId(complaintType);
        complaintDto.setDescription(complaintText); // complaintText를 description에 설정
        complaintDto.setReason(complaintTitle);     // complaintTitle을 reason에 설정
        complaintDto.setRequestDatetime(Timestamp.valueOf(currentDateTime)); // 현재 시간 넣기

        // Repository를 통해 데이터 저장
        complaintRepository.insertComplaint(complaintDto);
    }


    // complaintId로 민원 조회
    public ComplaintDto getComplaintById(String complaintId) {
        return complaintRepository.findComplaintById(complaintId);
    }

}