package org.example.shoppingmall.dto.order;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Data
public class ProductInfoDto {
    private String name;
    private BigDecimal price;
    private String color;
    private String size;
}
