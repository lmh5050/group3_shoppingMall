package org.example.shoppingmall.repository.product;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.product.ProductLike;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface ProductLikeRepository {
    void setLikeProductById(ProductLike productLike);

    ArrayList<String> getLikeProductById(String userId);

    ProductLike getCheckLikeExists(String productId, String userId);

    void updateProductLikeCountPlus(String productId);

    void updateProductLikeCountMinus(String productId);

    void deleteProductLike(String productId);

}
