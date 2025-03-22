package org.example.shoppingmall.dto.product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.Date;

@Setter
@Getter
@Data
public class ProductDto{  //DTO

    private String productId;
    private int sellerId;
    private int seasonId;
    private String shippingCompanyId;
    private String categoryId;
    private String name;
    private String description;
    private int price;
    private Date registerDatetime;
    private Date updatedDatetime;
    private String features;
    private String notes;
    private int accessCount;
    private int searchCount;
    private String status;
    private int newProductFlag;
    private int likeCount;
    private String gender;
    private int reviewCount;
    private int inquiryCount;
    private String fit;
    private String texture;
    private String thickness;
    private int rating;
    private int totalSales;
    private String manufacturer;
    private String origin;
    private String qualityAssuranceStandard;
    private int colorCount;
    private int sizeCount;
    private boolean activeFlag;
}