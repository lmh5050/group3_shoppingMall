package org.example.shoppingmall.controller.product;

import org.example.shoppingmall.dto.product.ProductCategoryDto;
import org.example.shoppingmall.dto.product.ProductDto;
import org.example.shoppingmall.service.product.ProductCategoryService;
import org.example.shoppingmall.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        System.out.println( products);
        for(ProductDto productDto : products) {
            System.out.println(productDto);
        }
        return "index";
    }

//    상세 페이지 이동
    @GetMapping("/productDetail")
    public String productDetail(String prdId, Model model) {
        System.out.println("prdId:"+prdId);
        model.addAttribute("prdId", prdId);

        // 서비스 측 구현할 것: 상품 ID를 통해 ProductDto 가져오기
        ProductDto product = productService.getProductById(prdId);
        model.addAttribute("product", product);

        return "indexDetail";
    }

//    카테고리 이동
    @GetMapping("/category")
    public String categoryList(Model model) {
        ArrayList<ProductCategoryDto> categoryList  = productCategoryService.getCategoryListAll();
        model.addAttribute("categoryList", categoryList);
        System.out.println("categoryList = " + categoryList);
        System.out.println();

        ArrayList<ProductCategoryDto> productCategory = productCategoryService.getCategoryByPId("ALL");
        System.out.println("productCategory = " + productCategory);
        System.out.println();

        ArrayList<ProductCategoryDto> majorCategory = productCategoryService.getMajorCategoryByPId();
        model.addAttribute("majorCategory", majorCategory);
        System.out.println("majorCategory = " + majorCategory);

        return "/product/category";
    }
}
