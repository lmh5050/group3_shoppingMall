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

    // 숫자로만 이루어진 UUID 생성 메서드
    private String generateNumericUUID() {
        String uuid = UUID.randomUUID().toString().replaceAll("[^0-9]", ""); // 숫자만 남기기
        return uuid.substring(0, 8); // 길이 : 8자리
    }
    
    //민원 저장
    public void saveComplaint(String complaintType, String complaintTitle, String complaintText,String pickupAddress) {
        // 숫자로만 이루어진 complaint_id 생성
        String complaintId = generateNumericUUID();

        // 현재 시간을 request_datetime에 넣기 위해 LocalDateTime 사용
        LocalDateTime currentDateTime = LocalDateTime.now();

        // 배송비 설정 (취소 = 0원, 환불/교환 = 5000원)
        double shippingPrice = 0;
        if ("refund".equals(complaintType) || "exchange".equals(complaintType)) {
            shippingPrice = 5000;
        }


        // ComplaintDto 객체에 값 설정
        ComplaintDto complaintDto = new ComplaintDto();
        complaintDto.setComplaintId(complaintId);
        complaintDto.setComplaintTypeId(complaintType);
        complaintDto.setReason(complaintTitle);     // complaintTitle을 reason에 설정
        complaintDto.setPickupAddress(pickupAddress);
        complaintDto.setDescription(complaintText); // complaintText를 description에 설정
        complaintDto.setRequestDatetime(Timestamp.valueOf(currentDateTime)); // 현재 시간 넣기
        complaintDto.setShippingPrice(shippingPrice);

        // Repository를 통해 데이터 저장
        complaintRepository.insertComplaint(complaintDto);
    }

    // complaintId로 민원 조회
    public ComplaintDto getComplaintById(String complaintId) {
        return complaintRepository.findComplaintById(complaintId);
    }

    //민원 수정 메서드
    public void updateComplaint(String complaintId, String complaintType, String complaintTitle, String complaintText, String pickupAddress) {
        // 배송비 설정 (취소 = 0원, 환불/교환 = 5000원)
        double shippingPrice = 0;
        if ("refund".equals(complaintType) || "exchange".equals(complaintType)) {
            shippingPrice = 5000;
        }

        ComplaintDto complaintDto = new ComplaintDto();
        complaintDto.setComplaintId(complaintId);
        complaintDto.setComplaintTypeId(complaintType);
        complaintDto.setReason(complaintTitle);
        complaintDto.setPickupAddress(pickupAddress);
        complaintDto.setDescription(complaintText);
        complaintDto.setShippingPrice(shippingPrice);


        // Repository를 통해 데이터 업데이트
        complaintRepository.updateComplaint(complaintDto);
    }

    //민원 삭제 메서드
    public void deleteComplaint(String complaintId) {
        ComplaintDto complaintDto = new ComplaintDto();
        complaintDto.setComplaintId(complaintId);

        complaintRepository.deleteComplaint(complaintDto);
    }

}
