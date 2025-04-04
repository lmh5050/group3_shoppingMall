package org.example.shoppingmall.controller.product;

import jakarta.servlet.http.HttpSession;
import org.example.shoppingmall.dto.product.ProductCategoryDto;
import org.example.shoppingmall.dto.product.ProductSortDto;
import org.example.shoppingmall.service.product.ProductCategoryService;
import org.example.shoppingmall.service.product.ProductLikeService;
import org.example.shoppingmall.service.product.ProductQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Optional;

@ControllerAdvice
public class ProductControllerAdvice {
    private final ProductCategoryService productCategoryService;
    private final ProductQueryService productQueryService;
    private final ProductLikeService productLikeService;

    @Autowired
    public ProductControllerAdvice(
            ProductCategoryService productCategoryService,
            ProductQueryService productQueryService,
            ProductLikeService productLikeService) {
        this.productCategoryService = productCategoryService;
        this.productQueryService = productQueryService;
        this.productLikeService = productLikeService;
    }

    // 모든 페이지에서 카테고리와 정렬 리스트를 자동으로 추가
    @ModelAttribute
    public void addCommonAttributes(Model model, HttpSession session) {
        // 대분류 카테고리 가져오기
        ArrayList<ProductCategoryDto> categoryList = productCategoryService.getMajorCategoryByPId();
        model.addAttribute("categoryList", categoryList);

        // 정렬 옵션 리스트 가져오기
        ArrayList<ProductSortDto> sortList = productQueryService.getProductSortOptions();
        model.addAttribute("sortList", sortList);

        // 세션에서 유저 ID 가져오기
        String userId = Optional.ofNullable(session.getAttribute("customerId"))
                .map(Object::toString)
                .orElse(null);
        model.addAttribute("userId", userId);

        // 로그인한 경우 좋아요 목록 추가
        if (userId != null) {
            ArrayList<String> userLike = productLikeService.getLikeProductById(userId);
            model.addAttribute("userLike", userLike);
        }
    }
}
