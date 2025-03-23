package org.example.shoppingmall.service;

import org.example.shoppingmall.dto.CodeDetailDto;
import org.example.shoppingmall.repository.CodeDetailRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CodeDetailService {
    private final CodeDetailRepository codeDetailRepository;

    public CodeDetailService(CodeDetailRepository codeDetailRepository) {
        this.codeDetailRepository = codeDetailRepository;
    }

//    관련된 상태 코드들만 가져오기
    public ArrayList<CodeDetailDto> getCodeDataByCodeCategory(String codeCategory) {
        return codeDetailRepository.getCodeDataByCodeCategory(codeCategory);
    }
}
