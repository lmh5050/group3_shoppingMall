package org.example.shoppingmall.dto.order;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Timestamp createdAt;
    private String codeName;
    private Long orderId;
    private Long serialNumber;
    private String customerId;
    private Integer totalQuantity;
    private BigDecimal finalAmount;
    private String productName;
    private String color;
    private String size;
    private Integer quantity;
    private BigDecimal productTotalPrice;
}
