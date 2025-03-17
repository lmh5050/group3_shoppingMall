package org.example.shoppingmall.controller.product;

import org.example.shoppingmall.dto.product.ProductDto;
import org.example.shoppingmall.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String home(Model model) {
        ArrayList<ProductDto> products = productService.getProductData();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/product")
    public String productDetail(Model model) {
        return "product";
    }
}
