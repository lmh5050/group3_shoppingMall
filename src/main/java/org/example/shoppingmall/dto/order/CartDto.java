package org.example.shoppingmall.dto.order;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CartDto {
    private String customerId;
    private String productDetailId;
    private byte checkFlag;
    private int  quantity;
    private Timestamp registerDate;
    private Timestamp deleteDate;
    private byte activeFlag;
    private byte deleteFlag;

    // product_detail 테이블에서 가져온 컬럼들
    private String color;          // 색상
    private String size;           // 사이즈

    // product 테이블에서 가져온 컬럼들
    private String name;    // 상품명
    private String description;    // 상품 설명
    private double price;          // 상품 가격
}
