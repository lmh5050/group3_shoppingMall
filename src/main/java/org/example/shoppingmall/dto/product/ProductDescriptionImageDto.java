package org.example.shoppingmall.dto.product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProductDescriptionImageDto {
    private String imgIndex;
    private String productId;
    private String image;
    private String type;
    private int heightSize;
    private int widthSize;
    private String description;
    private String form;
}
