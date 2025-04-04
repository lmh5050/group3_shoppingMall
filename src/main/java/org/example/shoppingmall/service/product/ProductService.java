package org.example.shoppingmall.service.product;

import org.example.shoppingmall.dto.product.*;
import org.example.shoppingmall.repository.product.ProductDetailRepository;
import org.example.shoppingmall.repository.product.ProductLikeRepository;
import org.example.shoppingmall.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

// 서비스 로직 다룸
@Service
public class ProductService {
    private final ProductQueryService productQueryService;

    public ProductService(ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }

    // 정렬 순서를 정한 경우
    public ArrayList<ProductDto> selectOrderOption(String orderOption){
        if (orderOption  != null){
            return productQueryService.getProductOrderByOptions(orderOption);
        } else {
            return productQueryService.getProductData();
        }
    }

    // 상품 좋아요 테이블에 들어갈 DTO 준비
    public ProductLikeDto preprocessingSetLikeProduct(String userId, String likeProduct){
        ProductLikeDto productLikeDto = new ProductLikeDto();
        // 유저 아이디 가져오기 & 넣기
        productLikeDto.setUserId(userId);
        // 상품 아이디 정보 넣기
        productLikeDto.setProductId(likeProduct);
        return productLikeDto;
    }
}
// 기존 옵션에 존재하는 데이터인지 확인
//    private void setProductOptions(List<String> sizes, List<String> colors, String productId) {
// 원래 기존에 존재하는 색상 * 사이즈는 그냥 DB에 냅두고, 새롭게 생기는 색상 * 사이즈만 추가하려고했는데
// 잘 안되서 일단 관련된 모든 디테일 정보 삭제 후, 새로 insert하는 방향으로 가기로 결정
// 일단 전처리까지는 잘 돌아가는데, DB에서 기존에 존재하는 컬럼 삭제가 잘 안됨
//        // 기존에 존재하던 옵션들을 중복 제거
//        HashSet<String> existenceColor = this.getProductDetailOption(productId, "color");
//        HashSet<String> existenceSize = this.getProductDetailOption(productId, "size");
//
//        // 중복 제거 후, 각 사이즈와 컬러를 비교하여 이미 존재하는 애들만 남겨둠
//        Iterator<String> colorIterator = existenceColor.iterator();
//        while (colorIterator.hasNext()) {
//            if (!colors.contains(colorIterator.next())) {
//                colorIterator.remove();
//            }
//        }
//        Iterator<String> sizeIterator = existenceSize.iterator();
//        while (sizeIterator.hasNext()) {
//            if (!sizes.contains(sizeIterator.next())) {
//                sizeIterator.remove();
//            }
//        }
//
//        System.out.println("중복 제거 후, 이미 존재하는 애들을 출력");
//        System.out.println("existenceSize = " + existenceSize);
//        System.out.println("existenceColor = " + existenceColor);
//
//        // DB에서 이미 존재하는 애들을 제외한 나머지를 DB에서 삭제함
//        List<Map<String, String>> colorAndSize = new ArrayList<>();
//        for (String color : existenceColor) {
//            for (String size : existenceSize) {
//                Map<String, String> map = new HashMap<>();
//                map.put("color", color);
//                map.put("size", size);
//                colorAndSize.add(map);
//            }
//        }
//        Map<String, Object> pair = new HashMap<>();
//        pair.put("productId", productId);
//        pair.put("validColorSizePairs", colorAndSize); // List<Map<String, String>> 형태
//        productDetailRepository.removeByExcludedColorAndSize(pair);

// detail 테이블에 컬럼 생성 -> 이미 존재하는 색상 * 컬러는 제외
//        this.updateDetailOptions(productId, colors, existenceColor, sizes, existenceSize);
//        this.updateDetailOptions(productId, colors, sizes);
//    }


// 디테일 옵션 만들고 저장
//    private void updateDetailOptions(String productId, List<String> colors, HashSet<String> existenceColor, List<String> sizes, HashSet<String> existenceSize) {