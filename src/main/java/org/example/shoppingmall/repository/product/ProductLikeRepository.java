package org.example.shoppingmall.repository.product;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.product.ProductLike;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface ProductLikeRepository {
    void setLikeProductById(ProductLike productLike);

    ArrayList<String> getLikeProductById(String userId);

    ProductLike checkLikeExists(String productId, String userId);

    void setProductLikeCountPlus(String productId);

    void setProductLikeCountMinus(String productId);

    void deleteProductLike(String productId);

}
