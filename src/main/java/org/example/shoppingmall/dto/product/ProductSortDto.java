package org.example.shoppingmall.dto.product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProductSortDto {
    private String productSortId;
    private String explanation;
    private int order;
}
