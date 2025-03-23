package org.example.shoppingmall.dto.complaint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
@Getter
@Setter
@ToString
public class ComplaintDto {
    private String complaintId;            // complaint_id
    private String complaintTypeId;        // complaint_type_id
    private String refundMethodId;         // refund_method_id
    private String orderDetailId;          // order_detail_id
    private String employeeId;             // employee_id
    private String reason;                 // reason
    private String pickupAddress;          // pickup_address
    private String returnAddress;          // return_address
    private String status;                    // status
    private String description;            // description
    private Timestamp requestDatetime;     // request_datetime
    private Timestamp receivedDatetime;    // received_datetime
    private Timestamp endDatetime;         // end_datetime
    private String comment;                // comment
    private String returnMethod;           // return_method
    private Double expectedRefundAmount;   // expected_refund_amount
    private Double refundAmount;           // refund_amount
    private Double shippingPrice;          // shipping_price
    private Byte deleteFlag;               // delete_flag
    private String exchangeProductId;      // exchange_product_id
}
