package org.example.shoppingmall.dto.product;

import lombok.Data;

import java.util.Date;

@Data
public class ProductLike {
    private String productId;
    private String userId;
    private Date likedDate;
}
