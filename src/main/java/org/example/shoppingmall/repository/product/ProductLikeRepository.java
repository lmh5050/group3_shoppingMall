package org.example.shoppingmall.repository.product;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.product.ProductLikeDto;

import java.util.ArrayList;

@Mapper
public interface ProductLikeRepository {
    void setLikeProductById(ProductLikeDto productLikeDto);

    ArrayList<String> getLikeProductById(String userId);

    ProductLikeDto getCheckLikeExists(String productId, String userId);

    void updateProductLikeCountPlus(String productId);

    void updateProductLikeCountMinus(String productId);

    void deleteProductLike(String productId);

}
