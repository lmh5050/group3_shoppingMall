package org.example.shoppingmall.repository.product;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.product.ProductDetailDto;
import org.example.shoppingmall.dto.product.ProductDto;
import org.example.shoppingmall.dto.product.ProductSortDto;
import org.example.shoppingmall.dto.product.ProductUpdateDto;

import java.util.ArrayList;

@Mapper
public interface ProductRepository {  // Repository
    ArrayList<ProductDto> getProductData();

    void setNewProduct(ProductDto productDto);

    ProductDto getProductById(String productId);

    ArrayList<ProductDetailDto> getProductDetailOptions(String productId);

    ArrayList<ProductDto> getProductOrderByOptions(String orderOption);

    ArrayList<ProductDto> getProductBySearch(String search);

    ArrayList<ProductSortDto> getProductSortOptions();

    ArrayList<ProductDto> getCategoryProductWithOrderOption(ArrayList<String> productIdList, String order);

    void setProductStatus(String productId, String status);

    void setProductInfo(ProductUpdateDto productUpdateDto);
}

