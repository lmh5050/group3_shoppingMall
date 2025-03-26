package org.example.shoppingmall.dto.shipping;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Data
public class ShippingDto {

    private String shippingId;
    private String orderDetailId;
    private String shippingCompanyId;
    private Integer quantity;
    private Double amount;
    private Double weight;
    private String trackingNumber;
    private String status;
    private String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date  shippingDatetime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date  expectedDatetime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDatetime;
    private String supplier;
    private String note;
    private String delayReason;
    private String description;
    private String trackingUrl;
    private Double shippingPrice;
    private String region;
    private String deliveryName;
    private String deliveryNumber;
    private Date deliveryDatetime;
    private String returnAddress;
    private String returnNumber;
    private String returnName;
    private Date createdAt;
    private Date updatedAt;
    private Boolean deleteFlag;
    private Long shippingCompanyNo;

}