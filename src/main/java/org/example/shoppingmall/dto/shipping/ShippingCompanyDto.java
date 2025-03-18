package org.example.shoppingmall.dto.shipping;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;


@Setter
@Getter
@Data
public class ShippingCompanyDto {
    private String shippingCompanyId; // `shipping_company_id`
    private String employeeId;        // `employee_id`
    private String companyName;       // `company_name`
    private String managerName;       // `manager_name`
    private String phoneNumber;       // `phone_number`
    private String email;             // `email`
    private String companyPhone;      // `company_phone`
    private String location;          // `location`
    private String note;              // `note`
    private Date managerRegisterDatetime; // `manager_register_datetime`
    private Date employeeRegisterDatetime; // `employee_register_datetime`
    private Date updatedAt;  // `updated_at`
    private int shippingCompanyNo;

}



