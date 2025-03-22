package org.example.shoppingmall.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CodeDetailDto {
    private String codeNo;
    private String codeName;
    private int codeCategory;
    private String codeStatusDesc;
    private boolean activeFlag;
    private boolean deleteFlag;
}
