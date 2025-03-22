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

    void responseCustomerComplaint(ComplaintDto complaintDto);

    void deleteCustomerComplaint(ComplaintDto complaintDto);
}
