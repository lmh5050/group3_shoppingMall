package org.example.shoppingmall.dto.shipping;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@Data
public class ShippingDto {
//
//    private Long shippingCompanyId;
//    private Long employeeId;
//    private String companyName;
//    private String managerName;
//    private String phoneNumber;
//    private String email;
//    private String companyPhone;
//    private String location;
//    private String note;
//    private Timestamp managerRegisterDatetime;
//    private Timestamp employeeRegisterDatetime;
//    private Timestamp updatedAt;
//    private String shippingCompanyNo;

    private Long id;
    private String boardTitle;
    private String boardWriter;
    private String boardPass;
    private String boardContents;
    private String boardHits;
    private Date createdAt;
}

