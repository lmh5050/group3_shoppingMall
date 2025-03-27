package org.example.shoppingmall.repository.complaint;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.complaint.ComplaintDto;
import org.springframework.stereotype.Repository;

import java.io.Serial;
import java.util.List;

@Mapper
@Repository
public interface ComplaintRepository {
//    // 모든 complaint 목록 조회
//    List<ComplaintDto> findAllComplaints();

    //민원 저장
    void insertComplaint(ComplaintDto complaintDto);

    //complaintId로 조회
    ComplaintDto findComplaintById(String complaintId);

    // customerId로 민원 목록 조회
    List<ComplaintDto> findComplaintsByCustomerId(String customerId);

    //민원 수정 메서드
    void updateComplaint(ComplaintDto complaintDto);

    //민원 삭제 메서드
    void deleteComplaint(ComplaintDto complaintDto);

    //상품 이름 조회
    List<String> findProductNameByOrderId(Long orderId);

    //상품 가격 조회
    String findProductTotalPriceByOrderId(Long orderId);

    boolean existsByOrderIdAndProductNameAndStatusNotContaining(Long orderId, String productName, String status);

}