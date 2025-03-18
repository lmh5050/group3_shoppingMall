package org.example.shoppingmall.dto.order;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class OrderDetailDto {
    private String orderDetailId;
    private String orderId;
    private String productDetailId;
    private String sellerId;
    private String productName;
    private String size;
    private String color;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal vat; // VAT은 Mapper에서 자동 계산됨
    private BigDecimal discountAmount;
    private BigDecimal totalOrderAmount;
    private BigDecimal totalProductAmount;
    private Integer status;
}
