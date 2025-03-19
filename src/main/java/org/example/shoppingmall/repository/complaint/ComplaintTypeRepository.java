package org.example.shoppingmall.repository.complaint;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.complaint.ComplaintTypeDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ComplaintTypeRepository {
    List<ComplaintTypeDto> findAll();
}
