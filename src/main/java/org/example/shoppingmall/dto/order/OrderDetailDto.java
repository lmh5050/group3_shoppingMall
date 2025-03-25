package org.example.shoppingmall.dto.order;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class OrderDetailDto {
    private Timestamp createdAt;
    private Long orderId;
    private String codeName;
    private BigDecimal totalAmount; //전체상품총금액
    private BigDecimal totalDiscountAmount; //상품총할인
    private BigDecimal discountAmount; //결제할인
    private BigDecimal finalAmount; //최종결제금액
    private String bankDeposit;
    private String accountDeposit;
    private String name; //결제수단명
    private String basicAddress;
    private String detailAddress;
    private String receivePeople;
    private String receivePhoneNumber;
    private BigDecimal shippingPrice; //배송비
    private Long serialNumber;
    private String productName;
    private String size;
    private String color;
    private Integer quantity;
    private String productTotalPrice;
    private String productId;
    public String getImageUrl() {
        return "/images/product/" + this.productId + ".png";
    }
}
