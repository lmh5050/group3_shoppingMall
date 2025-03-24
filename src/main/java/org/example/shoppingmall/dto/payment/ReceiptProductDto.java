package org.example.shoppingmall.dto.payment;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReceiptProductDto {
    private String productName;     
    private String color;           
    private String size;            
    private Integer quantity;       
    private Integer productPrice;   
} 