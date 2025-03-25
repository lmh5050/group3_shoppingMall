package org.example.shoppingmall.dto.order;

import lombok.Data;
import java.sql.Timestamp;


@Data
public class OrderListDto {
    private Timestamp createdAt;
    private Integer orderId;
    private String orderStatus;
    private String productName;
    private String size;
    private String color;
    private Integer quantity;
    private String productTotalPrice;
    private String productId;
    private String codeName;

    public String getImageUrl() {
        return "/images/product/" + this.productId + ".png";
    }
}