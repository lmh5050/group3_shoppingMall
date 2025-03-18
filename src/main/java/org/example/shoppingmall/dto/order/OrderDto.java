package org.example.shoppingmall.dto.order;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class OrderDto {
    private String orderId;
    private String customerId;
    private String orderStatus;
    private String paymentStatus;
    private BigDecimal totalOrderAmount;
    private BigDecimal totalDiscountAmount;
    private BigDecimal shippingFee;
    private Integer totalQuantity;
    private BigDecimal finalPaymentAmount;
    private String paymentTransactionId;
    private String recipient;
    private Integer postalCode;
    private String shippingAddressBasic;
    private String shippingAddressDetail;
    private String recipientContact;
    private String shippingRequest;
    private Timestamp orderDatetime;
    private Timestamp paymentCompletionDatetime;
    private Timestamp shippingStartDatetime;
    private Timestamp shippingCompletionDatetime;
    private Timestamp orderCancellationDatetime;
    private Timestamp refundCompletionDatetime;
    private String adminNote;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
