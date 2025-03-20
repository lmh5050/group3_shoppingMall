package org.example.shoppingmall.dto.order;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductInfoDto {
    private String productId;
    private String productDetailId;
    private String name;
    private BigDecimal price;
    private String color;
    private String size;
    private Integer quantity;
    private BigDecimal discount;
    private Integer orderDetailId;
    private BigDecimal totalPrice;

    public BigDecimal getTotalPrice() {
        if (price != null && quantity != null) {
            return price.multiply(BigDecimal.valueOf(quantity));  // BigDecimal의 multiply() 사용
        }
        return BigDecimal.ZERO;  // price나 quantity가 null일 경우 0 반환
    }

    public String getImageUrl() {
        return "/images/product/" + this.productId + ".png";
    }
}


