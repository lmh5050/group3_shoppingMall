package org.example.shoppingmall.dto.product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@Data
public class ProductUpdateDto {
    private String productId;                // 상품 ID
    private String productName;              // 상품 이름
    private Double productPrice;             // 상품 가격
    private String productDescription;       // 상품 설명
    private String productCategoryId;        // 카테고리 ID
    private String productSessionId;         // 시즌 ID
    private String productNotes;             // 비고
    private String productGender;            // 성별
    private String productFit;               // 핏
    private String productTexture;           // 촉감
    private String productThickness;         // 두께
    private String productManufacturer;      // 제조사
    private String productOrigin;            // 원산지
    private String productQualityAssuranceStandard;  // 품질 보증 기준
//    private List<String> detailColor;        // 색상
//    private List<String> detailSize;         // 사이즈
    private List<String> colors;        // 색상
    private List<String> sizes;         // 사이즈
    private int colorCount;
    private int sizeCount;
    private MultipartFile image;
    private String status;

    public ProductUpdateDto() {
        this.status = "display";
        this.colorCount=0;
        this.sizeCount=0;
    }
}
