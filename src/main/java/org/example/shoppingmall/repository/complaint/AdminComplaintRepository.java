package org.example.shoppingmall.repository.complaint;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.complaint.ComplaintDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminComplaintRepository {
    List<ComplaintDto> findCustomerComplaints();

    ComplaintDto findCustomerComplaintById(String complaintId);

    //고객 민원 접수
    void receivedCustomerComplaint(ComplaintDto complaintDto);

    //고객 민원 답변
    void responseCustomerComplaint(ComplaintDto complaintDto);

    //고객 민원 삭제
    void deleteCustomerComplaint(ComplaintDto complaintDto);

    //상품 가격 조회
    String findProductTotalPriceByOrderId(Long orderId);
}
