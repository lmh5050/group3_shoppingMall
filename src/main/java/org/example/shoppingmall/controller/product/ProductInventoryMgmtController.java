package org.example.shoppingmall.controller.product;

import jakarta.servlet.http.HttpServletRequest;
import org.example.shoppingmall.dto.product.ProductDto;
import org.example.shoppingmall.dto.product.ProductStatusDto;
import org.example.shoppingmall.service.CodeDetailService;
import org.example.shoppingmall.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/manager")
public class ProductInventoryMgmtController {
    private final ProductService productService;
    private final CodeDetailService codeDetailService;

    @Autowired
    public ProductInventoryMgmtController(ProductService productService, CodeDetailService codeDetailService) {
        this.productService = productService;
        this.codeDetailService = codeDetailService;
    }


//    관리자 - 상품 관리 화면
    @GetMapping("/productInventoryMgmt")
    public String productInventoryMgmt(Model model) {
        // 저장되어 있는 상품들 가져오기
        ArrayList<ProductDto> products = productService.getProductData();
        model.addAttribute("products", products);

        return "/product/Product_inventory_mgmt";
    }

//    관리자 - 관리자가 상품의 진열 상태를 변경할 때
    @PostMapping("/productInventoryMgmt")
    @ResponseBody
    public ResponseEntity<Boolean> changeProductStatus(
            @RequestBody ProductStatusDto productStatusDto, Model model) {
        // 상품의 상태를 변경
        productService.setProductStatus(productStatusDto.getProductId(), productStatusDto.getSelectedOption());
        return ResponseEntity.ok(true);
    }


//    관리자 - 상품 정보 수정 화면
    @GetMapping("/productInventoryMgmt/updateProductDetail")
    public String productInventoryMgmtUpdateProductDetail(
            @RequestParam(name = "prdId") String productId,
            Model model) {
        ProductDto product = productService.getProductById(productId);
        model.addAttribute("product", product);

        return "/product/Product_detail_update";
    }
}
