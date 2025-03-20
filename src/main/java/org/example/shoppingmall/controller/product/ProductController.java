package org.example.shoppingmall.controller.product;

import org.example.shoppingmall.dto.product.ProductDto;
import org.example.shoppingmall.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

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

    @GetMapping("/productDetail")
    public String productDetail(String prdId, Model model) {
        System.out.println("prdId:"+prdId);
        model.addAttribute("prdId", prdId);

        // s서비스 측 구현할 것: 상품 ID를 통해 ProductDto 가져오기
        ProductDto product = productService.getProductById(prdId);
        model.addAttribute("product", product);

        return "indexDetail";
    }
}
