package org.example.shoppingmall.controller.product;

import org.example.shoppingmall.dto.product.ProductCategoryDto;
import org.example.shoppingmall.dto.product.ProductDetailDto;
import org.example.shoppingmall.dto.product.ProductDto;
import org.example.shoppingmall.service.product.ProductCategoryService;
import org.example.shoppingmall.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class ProductController {
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductController(ProductService productService,  ProductCategoryService productCategoryService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
    }

    //home
    @GetMapping("/")
    public String home(Model model) {
        ArrayList<ProductDto> products = productService.getProductData();
        model.addAttribute("products", products);

        ArrayList<ProductCategoryDto> list = productCategoryService.getMajorCategoryByPId();
        model.addAttribute("categoryList", list);

        return "index";
    }

//    상세 페이지 이동
    @GetMapping("/productDetail")
    public String productDetail(String prdId, Model model) {
        model.addAttribute("prdId", prdId);

        // 서비스 측 구현할 것: 상품 ID를 통해 ProductDto 가져오기
        ProductDto product = productService.getProductById(prdId);
        model.addAttribute("product", product);

        // 상품의 상세 옵션을 가져옴
        ArrayList<ProductDetailDto> productDetailOptions = productService.getProductDetailOptions(prdId);
        model.addAttribute("productDetailOptions", productDetailOptions);

        return "product/indexDetail";
    }

//    카테고리 이동
    @GetMapping("/category")
    public String categoryList(
            @RequestParam(name = "majorCID") String majorCID,
            @RequestParam(required = false, name = "midCID") String midCID,
            @RequestParam(required = false, name = "subCID") String subCID,
            Model model) {
        // 상품 전체 리스트 가져오기
        ArrayList<ProductDto> products;

        // 대분류 카테고리 가져오기
        ArrayList<ProductCategoryDto> list = productCategoryService.getMajorCategoryByPId();
        model.addAttribute("categoryList", list);

        // 중분류 카테고리 가져오기
        ArrayList<ProductCategoryDto> midCList = productCategoryService.getMiddleCategoryByPId(majorCID, "mid");
        model.addAttribute("midCategoryList", midCList);

        products = productService.getFilteredProductData(majorCID);

        // 중분류 카테고리가 선택된 경우
        ArrayList<ProductCategoryDto> subCList;
        if(midCID != null) {
            subCList = productCategoryService.getMiddleCategoryByPId(midCID, "sub");
            model.addAttribute("subCategoryList", subCList);
            // 중분류 상품(소분류도 포함)만 가져오는 서비스
            products = productService.getFilteredProductData(midCID);
        }

        // 소분류 카테고리가 선택된 경우
        if (subCID != null) {
            // 소분류 상품만 가져오는 서비스
            products = productService.getFilteredProductData(subCID);
        }

        model.addAttribute("products", products);
        return "/product/category";
    }
}
