package org.example.shoppingmall.dto.order;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class OrderDto {
    private Long orderId;                 // 주문번호
    private String customerId;              // 사용자 아이디
    private String orderStatus;             // 주문 상태
    private BigDecimal totalDiscountAmount; // 총 할인 금액
    private BigDecimal totalOrderAmount;    // 총 주문 금액
    private Integer totalQuantity;          // 총 수량
    private Integer totalTypeCnt;           // 주문 타입 수
    private Timestamp orderDatetime;        // 주문일시
    private Timestamp orderCancellationDatetime; // 주문취소일시
    private String adminNote;               // 관리자 메모
    private Boolean activeFlag;             // 활성 여부
    private Timestamp createdAt;            // 생성일시
    private Timestamp updatedAt;
}
