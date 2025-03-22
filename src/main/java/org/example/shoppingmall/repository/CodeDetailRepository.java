package org.example.shoppingmall.repository;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.CodeDetailDto;

import java.util.ArrayList;

@Mapper
public interface CodeDetailRepository {
    ArrayList<CodeDetailDto>  getCodeData();
    ArrayList<CodeDetailDto>  getCodeDataByCodeCategory(String codeCategory);
}
