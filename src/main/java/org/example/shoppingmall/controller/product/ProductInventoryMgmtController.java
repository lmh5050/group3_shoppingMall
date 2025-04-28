package org.example.shoppingmall.controller.product;

import org.example.shoppingmall.dto.product.*;
import org.example.shoppingmall.service.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.result.view.RedirectView;

import java.util.ArrayList;
import java.util.HashSet;

@Controller
@RequestMapping("/admin")
public class ProductInventoryMgmtController {
    private final ProductQueryService productQueryService;
    private final ProductManagementService productManagementService;
//    private final ProductUploadService productUploadService;
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductInventoryMgmtController(
            ProductQueryService productQueryService,
            ProductManagementService productManagementService,
//            ProductUploadService productUploadService,
            ProductCategoryService productCategoryService) {
        this.productQueryService = productQueryService;
        this.productManagementService = productManagementService;
//        this.productUploadService = productUploadService;
        this.productCategoryService = productCategoryService;
    }


//    관리자 - 상품 관리 화면
    @GetMapping("/product")
    public String productInventoryMgmt(Model model) {
        // 저장되어 있는 상품들 가져오기
        ArrayList<ProductDto> products = productQueryService.getProductData();
        model.addAttribute("products", products);

        return "/product/ProductInventoryMgmt";
    }

//    관리자 - 관리자가 상품의 진열 상태를 변경할 때
    @PostMapping("/product")
    @ResponseBody
    public ResponseEntity<Boolean> changeProductStatus(
            @RequestBody ProductStatusDto productStatusDto) {
        // 상품의 상태를 변경
        productManagementService.setProductStatus(productStatusDto.getProductId(), productStatusDto.getSelectedOption());
        return ResponseEntity.ok(true);
    }


//    관리자 - 상품 정보 수정 화면
    @GetMapping("/product/updateProductDetail")
    public String productInventoryMgmtUpdateProductDetail(
            @RequestParam(required = false, name = "prdId") String productId,
            @RequestParam(required = false, name = "successUpdate") String successUpdate,
            @RequestParam(required = false, name = "errorMessage") String errorMessage,
            Model model) {
        // 성공 후 돌아온 경우
        model.addAttribute("successUpdate", successUpdate);

        // 실패 후 돌아온 경ㅇ
        model.addAttribute("errorMessage", errorMessage);

        // 상품 정보 가져오기
        ProductDto product = productQueryService.getProductById(productId);
        model.addAttribute("product", product);

        // 색상/사이즈 정보 가져오기
        ArrayList<ProductDetailDto> productDetailOptions = productQueryService.getProductDetailOptions(productId);
        model.addAttribute("productDetailOptions", productDetailOptions);

        // 색상 정보 가져오기
        HashSet<String> detailColor = productManagementService.getProductDetailOption(productId, "color");
        model.addAttribute("detailColor", detailColor);

        // 컬러 정보 가져오기
        HashSet<String> detailSize = productManagementService.getProductDetailOption(productId, "size");
        model.addAttribute("detailSize", detailSize);

        // 카테고리 정보 가져오기
        ArrayList<ProductCategoryDto> category = productCategoryService.getSubCategory();
        model.addAttribute("category", category);

        // 시즌 정보 가져오기
        ArrayList<ProductSeasonDTO> season = productQueryService.getAllSeasonList();
        model.addAttribute("season", season);

       return "product/ProductDetailUpdate";
    }

    // 상품 정보 수정
    @PostMapping("/product/updateProductDetail")
    public String updateProductDetail(
            @ModelAttribute ProductUpdateDto productUpdateDto) {

        // 프론트 단에서 예외 처리 해야함
        String message = productManagementService.updateProductInfo(productUpdateDto);

        // 값이 옳지 않거나 오류가 나는 경우 메세지 전달
        if (message != null) {
            return "redirect:/product/updateProductDetail?prdId="+productUpdateDto.getProductId()+"&errorMessage=" + message;
        }

        // 성공했으면 successUpdate를 파라미터로 넘겨서 다시 GET 호출
        return "redirect:/product/updateProductDetail?prdId="+productUpdateDto.getProductId()+"&successUpdate=ok";
    }

    // 새로운 상품 추가 등록하기 위해 접근
    @GetMapping("/product/addNewProduct")
    public String addNewProduct(Model model) {

        // 카테고리 정보 가져오기
        ArrayList<ProductCategoryDto> productCategory = productCategoryService.getSubCategory();
        model.addAttribute("productCategory", productCategory);

        // 시즌 정보 가져오기
        ArrayList<ProductSeasonDTO> productSeason = productQueryService.getAllSeasonList();
        model.addAttribute("productSeason", productSeason);
        return "/product/NewProductPost";
    }

    // 새로운 상품 추가 등록하기
    @PostMapping("/product/addNewProduct")
    public String addNewProductPost(@ModelAttribute ProductUpdateDto productUpdateDto) {
        MultipartFile imageFile = productUpdateDto.getImage();

        // 상품 등록 결과 메시지
        String message = productManagementService.productUpload(imageFile, productUpdateDto);

        // 1. 성공한 경우
        if (!message.equals("성공")) {
            return "redirect:/admin/product/addNewProduct";
        }
        // 2. 실패한 경우
        return "/product/ProductInventoryMgmt";
    }
}
