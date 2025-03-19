package org.example.shoppingmall.repository.product;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.product.ProductCategoryDto;

import java.util.ArrayList;

@Mapper
public interface ProductCategoryRepository {  //카테고리 Repository
    ArrayList<ProductCategoryDto> getCategoryListAll();
    ArrayList<ProductCategoryDto> getCategoryByPId(String categoryId);
    ArrayList<ProductCategoryDto> getMajorCategoryByPId();
}
