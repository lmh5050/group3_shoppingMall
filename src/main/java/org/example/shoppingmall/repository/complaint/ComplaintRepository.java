package org.example.shoppingmall.repository.complaint;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.complaint.ComplaintDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ComplaintRepository {
    // 모든 complaint 목록 조회
    List<ComplaintDto> findAllComplaints();

    //민원 저장
    void insertComplaint(ComplaintDto complaintDto);

    //complaintId로 조회
    ComplaintDto findComplaintById(String complaintId);
}