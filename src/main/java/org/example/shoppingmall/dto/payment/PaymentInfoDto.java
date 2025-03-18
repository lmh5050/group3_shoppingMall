package org.example.shoppingmall.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentInfoDto {
    private String orderId;
    private Integer baseAmount;
    private Integer discountAmount;
    private Integer finalAmount;
}
