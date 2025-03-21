package org.example.shoppingmall.dto.order;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;


@Data
public class OrderListDto {
    private Timestamp createdAt;
    private String orderStatus;

    private String productId;
    private String productDetailId;
    private String product_name;
    private BigDecimal price;
    private String color;
    private String size;
    private Integer quantity;
    private BigDecimal discount;
    private Integer orderDetailId;
    private BigDecimal totalPrice;
}