package org.example.shoppingmall.controller.product;

import org.example.shoppingmall.dto.product.*;
import org.example.shoppingmall.service.CodeDetailService;
import org.example.shoppingmall.service.product.ProductCategoryService;
import org.example.shoppingmall.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

@Controller
@RequestMapping("/admin")
public class ProductInventoryMgmtController {
    private final ProductService productService;
    private final CodeDetailService codeDetailService;
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductInventoryMgmtController(ProductService productService, CodeDetailService codeDetailService, ProductCategoryService productCategoryService) {
        this.productService = productService;
        this.codeDetailService = codeDetailService;
        this.productCategoryService = productCategoryService;
    }


//    관리자 - 상품 관리 화면
    @GetMapping("/productInventoryMgmt")
    public String productInventoryMgmt(Model model) {
        // 저장되어 있는 상품들 가져오기
        ArrayList<ProductDto> products = productService.getProductData();
        model.addAttribute("products", products);

        return "/product/ProductInventoryMgmt";
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
            @RequestParam(required = false, name = "prdId") String productId,
            Model model) {
        ProductDto product = productService.getProductById(productId);
        model.addAttribute("product", product);

        ArrayList<ProductDetailDto> productDetailOptions = productService.getProductDetailOptions(productId);
        model.addAttribute("productDetailOptions", productDetailOptions);

        HashSet<String> detailColor = productService.getProductDetailOption(productId, "color");
        model.addAttribute("detailColor", detailColor);

        HashSet<String> detailSize = productService.getProductDetailOption(productId, "size");
        model.addAttribute("detailSize", detailSize);

        // 카테고리 정보 가져오기
       return "/product/ProductDetailUpdate";
    }

    // 상품 정보 수정
    @PostMapping("/productInventoryMgmt/updateProductDetail")
    public ResponseEntity<?> updateProductDetail(
            @ModelAttribute ProductUpdateDto productUpdateDto) {

        productService.setProductInfo(productUpdateDto);

        return ResponseEntity.ok("ok");
    }

    // 새로운 상품 추가 등록하기 위해 접근
    @GetMapping("/addNewProduct")
    public String addNewProduct(Model model) {

        // 카테고리 정보 가져오기
        ArrayList<ProductCategoryDto> productCategory = productCategoryService.getSubCategory();
        model.addAttribute("productCategory", productCategory);

        // 시즌 정보 가져오기
        ArrayList<ProductSeasonDTO> productSeason = productService.getAllSeasonList();
        model.addAttribute("productSeason", productSeason);
        return "/product/NewProductPost";
    }

    // 새로운 상품 추가 등록하기
    @PostMapping("/addNewProduct")
    public String addNewProductPost(@ModelAttribute ProductUpdateDto productUpdateDto) {
        MultipartFile imageFile = productUpdateDto.getImage();

        // 상품 등록 결과 메시지
        String message = productService.productUpload(imageFile, productUpdateDto);

        if (!message.equals("성공")) {
            return "redirect:/admin/addNewProduct";
        }
        return "redirect:/admin/productInventoryMgmt";
    }
}
