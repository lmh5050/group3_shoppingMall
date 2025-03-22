package org.example.shoppingmall.dto.order;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class OrderDetailDto {
    private Long orderId;          // 주문번호
    private Integer serialNumber;     // 주문일련번호
    private String productId;        // 상품번호
    private String productName;      // 상품명
    private String size;             // 사이즈
    private String color;            // 색상
    private Integer quantity;        // 수량
    private BigDecimal price;        // 단가
    private BigDecimal discountAmount;  // 상품 할인 금액
    private BigDecimal productPrice;    // 상품 가격(단가-상품할인)
    private BigDecimal productTotalPrice; // 상품 총 가격(상품가격*수량)
    private Integer status;          // 주문 상태
    private Boolean activeFlag;      // 활성 여부
    private Timestamp createdAt;     // 생성일시
    private Timestamp updatedAt;     // 수정일시
}
