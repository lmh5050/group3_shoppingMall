package org.example.shoppingmall.repository.product;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.product.ProductDetailDto;

import java.util.Map;

@Mapper
public interface ProductDetailRepository {
    void setProductDetailByColorsAndSizes(ProductDetailDto productDetailDto);
    String getLastProductDetailId();
//    void removeByExcludedColorAndSize(Map<String, Object> pair);
    void removeProductDetailsByProductId(String productId);
    void updateProductDetailsStatusByZero(String productId);
}
