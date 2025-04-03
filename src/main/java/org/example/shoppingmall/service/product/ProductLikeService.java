package org.example.shoppingmall.service.product;

import org.example.shoppingmall.dto.product.ProductDto;
import org.example.shoppingmall.dto.product.ProductLike;
import org.example.shoppingmall.repository.product.ProductLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class ProductLikeService {
    private final ProductLikeRepository productLikeRepository;
    private final ProductQueryService productQueryService;

    @Autowired
    public ProductLikeService(
            ProductLikeRepository productLikeRepository,
            ProductQueryService productQueryService) {
        this.productLikeRepository = productLikeRepository;
        this.productQueryService = productQueryService;
    }

    // 로그인된 유저가 상품 좋아요 리스트에 추가하기
    @Transactional
    public void setLikeProductById(ProductLike productLike) {
        // 기존에 좋아요 테이블에 존재하는지 확인
        ProductLike checkProductLike = this.checkLikeExists(productLike.getProductId(), productLike.getUserId());

        if (checkProductLike == null){ // 기존에 존재하지 않는 경우
            productLikeRepository.setLikeProductById(productLike);  //새로 등록
            productLikeRepository.updateProductLikeCountPlus(productLike.getProductId());  //상품 좋아요 수 ++
        } else { // 좋아요 취소
            productLikeRepository.deleteProductLike(productLike.getProductId());  //delete flag = 1오 만듬
            productLikeRepository.updateProductLikeCountMinus(productLike.getProductId());  //상품 좋아요 수 --
        }
    }

    // 상품을 좋아요 눌렀는지 확인하기(중복 확인을 위해)
    private ProductLike checkLikeExists(String productId, String userId) {
        return productLikeRepository.getCheckLikeExists(productId, userId);
    }

    // 로그인된 유저가 어떤 상품을 좋아요 눌렀는지 확인하기
    public ArrayList<String> getLikeProductById(String userId) {
        return productLikeRepository.getLikeProductById(userId);
    }

    // 좋아요한 상품만 dto 매핑하여 가져오기
    public ArrayList<ProductDto> getLikeProductList(String userId) {
        ArrayList<String> likeProductIds = this.getLikeProductById(userId);
        ArrayList<ProductDto> products = productQueryService.getProductData();
        ArrayList<ProductDto> likeProducts = new ArrayList<>();

        // 좋아요 한 상품만 필터링
        for(ProductDto productDto : products){
            for(String likeProductId : likeProductIds){
                if(productDto.getProductId().equals(likeProductId)){
                    likeProducts.add(productDto);
                }
            }
        }

        return likeProducts;
    }
}
