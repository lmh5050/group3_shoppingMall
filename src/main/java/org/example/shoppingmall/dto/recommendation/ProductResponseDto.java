package org.example.shoppingmall.dto.recommendation;

import lombok.Data;

@Data
public class ProductResponseDto {
    private Long item_id;
    private String item_description;
    private Integer rating;
    private String review_text;
}
