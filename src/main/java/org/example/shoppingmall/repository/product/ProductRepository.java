package org.example.shoppingmall.repository.product;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.product.ProductDto;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ProductRepository {  // org.example.shoppingmall.repository.product.ProductRepository
    ArrayList<ProductDto> getProductData();

    void setNewProduct(ProductDto productDto);

    ProductDto getProductById(String productId);
}
