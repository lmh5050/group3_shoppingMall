package org.example.shoppingmall.dto.complaint;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class ComplaintHistoryDto {
    private int complaintHistoryId;
    private String complaintId;
    private String complaintTypeId;
    private String refundMethodId;
    private String orderDetailId;
    private String employeeId;
    private String reason;
    private String pickupAddress;
    private String returnAddress;
    private String status;
    private String description;
    private Timestamp requestDatetime;
    private Timestamp receivedDatetime;
    private Timestamp endDatetime;
    private String comment;
    private String refundMethod;
    private Double expectedRefundAmount;
    private Double refundAmount;
    private Double shippingPrice;
    private String exchangeProductId;
    private Byte deleteFlag;
}
