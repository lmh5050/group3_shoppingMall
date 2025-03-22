package org.example.shoppingmall.controller.product;

import org.example.shoppingmall.dto.product.ProductDto;
import org.example.shoppingmall.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/manager")
public class ProductInventoryMgmtController {
    private final ProductService productService;

    @Autowired
    ProductInventoryMgmtController(ProductService productService) {
        this.productService = productService;
    }
//    관리자 - 제고 관리 화면
    @GetMapping("/productInventoryMgmt")
    public String productInventoryMgmt(Model model) {
        // 저장되어 있는 상품들 가져오기
        ArrayList<ProductDto> products = productService.getProductData();
        model.addAttribute("products", products);

        return "/product/Product_inventory_mgmt";
    }
}
