package org.example.shoppingmall.dto.product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class ProductLike {
    private String productId;
    private String userId;
    private Date likedDate;
    private boolean deleteFlag;
}
