package org.example.shoppingmall.dto.product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProductSeasonDTO {
    private Long seasonId;         // 시즌 ID
    private String name;           // 시즌 이름
    private Integer periodMonth;   // 시작 월
    private Integer durationPeriod; // 진행 기간
}
