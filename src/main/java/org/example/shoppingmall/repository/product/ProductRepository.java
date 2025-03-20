package org.example.shoppingmall.repository.product;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.product.ProductDetailDto;
import org.example.shoppingmall.dto.product.ProductDto;

import java.util.ArrayList;

@Mapper
public interface ProductRepository {  // ��ǰ Repository
    ArrayList<ProductDto> getProductData();

    void setNewProduct(ProductDto productDto);

    ProductDto getProductById(String productId);

    ArrayList<ProductDetailDto> getProductDetailOptions(String productId);
}
