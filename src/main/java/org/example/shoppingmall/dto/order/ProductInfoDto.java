package org.example.shoppingmall.dto.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfoDto {
    // 상품 상세 아이디, 수량, 상품 아이디
    private String productDetailId;
    private String productId;
    private String name;
    private BigDecimal price;
    private String color;
    private String size;
    private Integer quantity;
    private String discount;
}