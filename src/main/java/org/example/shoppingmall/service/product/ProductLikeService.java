package org.example.shoppingmall.service.product;

import org.example.shoppingmall.dto.product.ProductDto;
import org.example.shoppingmall.dto.product.ProductLikeDto;
import org.example.shoppingmall.repository.product.ProductLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

// 좋아요 기능
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
    public void setLikeProductById(ProductLikeDto productLikeDto) {
        // 기존에 좋아요 테이블에 존재하는지 확인
        ProductLikeDto checkProductLikeDto = this.checkLikeExists(productLikeDto.getProductId(), productLikeDto.getUserId());

        if (checkProductLikeDto == null){ // 기존에 존재하지 않는 경우
            productLikeRepository.setLikeProductById(productLikeDto);  //새로 등록
            productLikeRepository.updateProductLikeCountPlus(productLikeDto.getProductId());  //상품 좋아요 수 ++
        } else { // 좋아요 취소
            productLikeRepository.deleteProductLike(productLikeDto.getProductId());  //delete flag = 1오 만듬
            productLikeRepository.updateProductLikeCountMinus(productLikeDto.getProductId());  //상품 좋아요 수 --
        }
    }

    // 상품을 좋아요 눌렀는지 확인하기(중복 확인을 위해)
    private ProductLikeDto checkLikeExists(String productId, String userId) {
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
