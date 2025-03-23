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

    @Autowired
    private ComplaintStatusService complaintStatusService;

    public ComplaintService(ComplaintRepository complaintRepository, ComplaintStatusService complaintStatusService) {
        this.complaintRepository = complaintRepository;
        this.complaintStatusService = complaintStatusService;
    }

    // 모든 complaint 목록 조회
    public List<ComplaintDto> getAllComplaints() {
        return complaintRepository.findAllComplaints();
    }

    // UUID 생성 메서드
    private String createNumericUUID(String complaintType) {

        //유형따라 다른 접두사 추가
        String typeCode ="";

        switch (complaintType.toLowerCase()) {
            case "cancel":
                typeCode = "CA";
                break;
            case "refund":
                typeCode = "RE";
                break;
            case "exchange":
                typeCode = "EX";
                break;
        }

        //숫자만 포함
        String uuid = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        // 8자리 숫자만 가져오기
        String shortUUID = uuid.substring(0, 8);

        return typeCode + "-" + shortUUID;
    }

    // 배송비 설정 메서드 (취소 0원, 환불-교환 5000원)
    private double applyShippingPrice(String complaintType) {
        if ("refund".equals(complaintType) || "exchange".equals(complaintType)) {
            return 5000;
        }
        return 0;
    }


    //민원 저장
    public void saveComplaint(String complaintType, String complaintTitle, String complaintText,String pickupAddress) {
        // 숫자로만 이루어진 complaint_id 생성
        String complaintId = createNumericUUID(complaintType);

        // 현재 시간을 request_datetime 으로 넣기 위해 LocalDateTime 사용
        LocalDateTime currentDateTime = LocalDateTime.now();

        double shippingPrice = applyShippingPrice(complaintType);
        
        String status = complaintStatusService.applyComplaintStatusToRequest(complaintType);

        // ComplaintDto 객체에 값 설정
        ComplaintDto complaintDto = new ComplaintDto();
        complaintDto.setComplaintId(complaintId);
        complaintDto.setComplaintTypeId(complaintType);
        complaintDto.setReason(complaintTitle);     // complaintTitle을 reason에 설정
        complaintDto.setPickupAddress(pickupAddress);
        complaintDto.setStatus(status);
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
    public void updateComplaint(String complaintId, String complaintTitle, String complaintText, String pickupAddress) {

        ComplaintDto complaintDto = new ComplaintDto();
        complaintDto.setComplaintId(complaintId);
        complaintDto.setReason(complaintTitle);
        complaintDto.setPickupAddress(pickupAddress);
        complaintDto.setDescription(complaintText);

        // Repository를 통해 데이터 업데이트
        complaintRepository.updateComplaint(complaintDto);
    }

    //민원 삭제 플래그 활성화 메서드
    public void deleteComplaint(String complaintId, String complaintType) {
        //deleteFlag 활성화 변수
        Byte onDeleteFlag = 1;

        String status = complaintStatusService.applyComplaintStatusToDelete(complaintType);

        ComplaintDto complaintDto = new ComplaintDto();
        complaintDto.setComplaintId(complaintId);
        complaintDto.setDeleteFlag(onDeleteFlag);
        complaintDto.setStatus(status);

        complaintRepository.deleteComplaint(complaintDto);
    }

}
