package org.example.shoppingmall.dto.product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@Data
public class ProductDetailDto {
    private String productDetailId;
    private String productId;
    private String color;
    private String size;
    private int stockQuantity;
    private Date stockInDate;
    private Date expectedStockInDate;
    private String note;
    private String status;

    public ProductDetailDto() {
        this.stockQuantity = 0;
        this.note = null;
        this.status = "ACTIVE";
    }
}
