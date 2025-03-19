package org.example.shoppingmall.dto.complaint;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//complaintTypeId 테이블
@Getter
@Setter
@ToString
public class ComplaintTypeDto {
    private String complaintTypeId;
    private String name;
    private String description;
    private String parentId;
}
