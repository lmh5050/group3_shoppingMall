package org.example.shoppingmall.dto.payment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReceiptDto {
    // 주문 정보
    private Integer orderId;            
    private String customerName;                 
    private LocalDateTime orderDate;    
    
    // 구매 상품 정보
    private List<ReceiptProductDto> orderDetailList;  
    
    // 결제 정보
    private Integer totalOrderAmount;   
    private Integer totalDiscountAmount;
    private Integer finalAmount;        
    private String paymentMethod;              
    private String paymentStatus;   
    private String accountDeposit;
    private String bankDeposit;
}