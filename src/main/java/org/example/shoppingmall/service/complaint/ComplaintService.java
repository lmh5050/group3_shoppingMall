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

//    // 모든 complaint 목록 조회
//    public List<ComplaintDto> getAllComplaints() {
//        return complaintRepository.findAllComplaints();
//    }

    // UUID 생성 메서드
    private String createNumericUUID(String complaintType) {

        //유형따라 다른 접두사 추가
        String typeCode = switch (complaintType.toLowerCase()) {
            case "cancel" -> "CA";
            case "refund" -> "RE";
            case "exchange" -> "EX";
            default -> "";
        };

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


    //해당 고객의 민원 조회
    public List<ComplaintDto> getComplaintsByCustomerId(String customerId) {
        return complaintRepository.findComplaintsByCustomerId(customerId);
    }

    //민원 저장
    public void saveComplaint(String complaintType, String complaintTitle, String complaintText,String pickupAddress, Long orderId, String productName) {

        // 숫자로만 이루어진 complaint_id 생성
        String complaintId = createNumericUUID(complaintType);

        // 현재 시간을 request_datetime 으로 넣기 위해 LocalDateTime 사용
        LocalDateTime currentDateTime = LocalDateTime.now();

        //민원 타입따라 배송비 설정
        double shippingPrice = applyShippingPrice(complaintType);

        //민원 유형따라 상태값 설정
        String status = complaintStatusService.applyComplaintStatusToRequest(complaintType);

        // 환불 예상 금액(expected_refund_amount) 설정
        double expectedRefundAmount = 0.0;

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
        complaintDto.setOrderId(orderId);
        complaintDto.setProductName(productName);

        //취소, 환불인 경우에만 product_total_price = expectedRefundAmount
        if (complaintType.equals("cancel") || complaintType.equals("refund")) {
            //예상 환불 금액에 상품 금액 대입, ProductTotalPrice 는 String형이여서 형변환
            expectedRefundAmount = Double.parseDouble(complaintRepository.findProductTotalPriceByOrderId(orderId, productName));
            complaintDto.setExpectedRefundAmount((expectedRefundAmount));
        } else complaintDto.setExpectedRefundAmount(expectedRefundAmount); //교환인 경우 예상 금액 = 0;

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

        // 삭제 시 삭제시간을 완료 시간에 넣기 위해 생성
        LocalDateTime currentDateTime = LocalDateTime.now();

        String status = complaintStatusService.applyComplaintStatusToDelete(complaintType);

        double refundAmount = 0.0;

        ComplaintDto complaintDto = complaintRepository.findComplaintById(complaintId);
        complaintDto.setComplaintId(complaintId);
        complaintDto.setDeleteFlag(onDeleteFlag);

        //신청상태에서만 삭제하면 상태 철회로 변경
        if (complaintDto.getStatus().contains("신청")) {
            complaintDto.setStatus(status);
            //신청 취소 시 환불금액은 없음
            complaintDto.setRefundAmount(refundAmount);
        }

        //완료 시간 없을 시 완료 시간=삭제 시간
        if (complaintDto.getEndDatetime() == null) {
            complaintDto.setEndDatetime(Timestamp.valueOf(currentDateTime));
        }

        complaintRepository.deleteComplaint(complaintDto);
    }

    //같은 orderId를 가지고 있는 productName 값들 출력
    public List<String> findProductNameByOrderId(Long orderId) {
       return complaintRepository.findProductNameByOrderId(orderId);
    }

    // 상태 값이 "철회"가 아닌 민원이 존재하는지 확인
    public boolean isComplaintAlreadyExists(Long orderId, String productName) {
        return complaintRepository.existsValidStatus(orderId, productName, "철회");
    }


}
