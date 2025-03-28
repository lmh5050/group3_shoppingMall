package org.example.shoppingmall.repository.product;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.product.ProductDetailDto;

import java.util.ArrayList;

@Mapper
public interface ProductDetailRepository {
    void setProductDetailByColorsAndSizes(ProductDetailDto productDetailDto);
    String getLastProductDetailId();
}
