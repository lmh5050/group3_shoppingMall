package org.example.shoppingmall.dto.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfoDto {
    private String name;
    private BigDecimal price;
    private String color;
    private String size;
}