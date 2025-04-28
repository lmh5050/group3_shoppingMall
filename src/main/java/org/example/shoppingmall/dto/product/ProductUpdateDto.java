package org.example.shoppingmall.dto.product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@Data
public class ProductUpdateDto {
    private String productId;                // 상품 ID
    private String productName;              // 상품 이름 productName
    private Double productPrice;             // 상품 가격 productPrice
    private String productDescription;       // 상품 설명 productDescription
    private String productCategoryId;        // 카테고리 ID productCategoryId
    private String productSeasonId;         // 시즌 ID productSeasonId
    private String productNotes;             // 비고 productNotes
    private String productGender;            // 성별 productGender
    private String productFit;               // 핏 productFit
    private String productTexture;           // 촉감 productTexture
    private String productThickness;         // 두께 productThickness
    private String productManufacturer;      // 제조사 productManufacturer
    private String productOrigin;            // 원산지 productOrigin
    private String productQualityAssuranceStandard;  // 품질 보증 기준 productQualityAssuranceStandard
    private List<String> colors;        // 색상
    private List<String> sizes;         // 사이즈
    private List<String> newColors;
    private List<String> newSizes;
    private int colorCount;
    private int sizeCount;
    private MultipartFile image;
    private String status;
    private ArrayList<Boolean> colorStatus;
    private ArrayList<Boolean> sizeStatus;


    public ProductUpdateDto() {
        this.status = "display";
        this.colorCount=0;
        this.sizeCount=0;
    }
}
