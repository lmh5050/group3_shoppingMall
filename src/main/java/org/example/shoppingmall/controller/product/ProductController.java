package org.example.shoppingmall.controller.product;

import jakarta.servlet.http.HttpSession;
import org.example.shoppingmall.dto.product.*;
import org.example.shoppingmall.service.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;


@Controller
public class ProductController {
    private final ProductQueryService productQueryService;
    private final ProductLikeService productLikeService;
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductController(
            ProductQueryService productQueryService,
            ProductLikeService productLikeService,
            ProductCategoryService productCategoryService) {
        this.productQueryService = productQueryService;
        this.productLikeService = productLikeService;
        this.productCategoryService = productCategoryService;

    }

    //home
    @GetMapping("/")
    public String home(
            @RequestParam(required = false, name = "orderOption") String orderOption,
            @RequestParam(required = false, name = "likeProduct") String likeProduct,
            @RequestParam(required = false, name = "URL") String URL,
            HttpSession session,
            Model model) {

        ArrayList<ProductDto> products;

        // 정렬 순서를 정한 경우
        if (orderOption  != null){
            products = productQueryService.getProductOrderByOptions(orderOption);
        } else {
            products = productQueryService.getProductData();
        }

        model.addAttribute("products", products);

        ArrayList<ProductCategoryDto> list = productCategoryService.getMajorCategoryByPId();
        model.addAttribute("categoryList", list);

        ArrayList<ProductSortDto> sortList =  productQueryService.getProductSortOptions();
        model.addAttribute("sortList", sortList);


        String userId = Optional.ofNullable(session.getAttribute("customerId"))
                .map(Object::toString)
                .orElse(null);

        // 로그인이 되어 있을 경우
        if (userId != null) {
            // 현재 로그인된 유저가 좋아요 한 상품 표시하기
            ArrayList<String> likeInfo = productLikeService.getLikeProductById(userId);

            model.addAttribute("userLike", likeInfo);
        }

        // 좋아요를 누른 경우 -> js에서 이미 세션 체크를 거치므로 무조건 로그인이 되어있는 상태
        if (likeProduct != null){
            ProductLike productLike = new ProductLike();
            // 유저 아이디 가져오기 & 넣기
            productLike.setUserId(userId);
            // 상품 아이디 정보 넣기
            productLike.setProductId(likeProduct);

            // 서비스에서 좋아요를 등록
            productLikeService.setLikeProductById(productLike);
            System.out.println(URL.substring(0, URL.indexOf("likeProduct")));
            return "redirect:/"+URL.substring(0, URL.indexOf("likeProduct"));
        }
        return "index";
    }


//    상세 페이지 이동
    @GetMapping("/productDetail")
    public String productDetail(
            String prdId,
            @RequestParam(required = false, name = "likeProduct") String likeProduct,
            @RequestParam(required = false, name = "URL") String URL,
            HttpSession session,
            Model model) {
        model.addAttribute("prdId", prdId);

        // 서비스 측 구현할 것: 상품 ID를 통해 ProductDto 가져오기
        ProductDto product = productQueryService.getProductById(prdId);
        model.addAttribute("product", product);

        // 시즌 정보 가져오기
        String season = productQueryService.getSeasonBySeasonId(product.getSeasonId());
        model.addAttribute("season", season);

        // 상품의 상세 옵션을 가져옴
        ArrayList<ProductDetailDto> productDetailOptions = productQueryService.getProductDetailOptions(prdId);
        model.addAttribute("productDetailOptions", productDetailOptions);
        String userId = Optional.ofNullable(session.getAttribute("customerId"))
                .map(Object::toString)
                .orElse(null);

        // 로그인이 되어 있을 경우
        if (userId != null) {
            // 현재 로그인된 유저가 좋아요 한 상품 표시하기
            ArrayList<String> likeInfo = productLikeService.getLikeProductById(userId);

            model.addAttribute("userLike", likeInfo);
        }

        // 좋아요를 누른 경우 -> js에서 이미 세션 체크를 거치므로 무조건 로그인이 되어있는 상태
        if (likeProduct != null){
            ProductLike productLike = new ProductLike();
            // 유저 아이디 가져오기 & 넣기
            productLike.setUserId(userId);
            // 상품 아이디 정보 넣기
            productLike.setProductId(likeProduct);

            // 서비스에서 좋아요를 등록
            productLikeService.setLikeProductById(productLike);
            System.out.println(URL.substring(0, URL.indexOf("likeProduct")));
            return "redirect:/productDetail"+URL.substring(0, URL.indexOf("likeProduct"));
        }

        return "product/indexDetail";
    }

//    카테고리 이동
    @GetMapping("/category")
    public String categoryList(
            @RequestParam(name = "majorCID") String majorCID,
            @RequestParam(required = false, name = "midCID") String midCID,
            @RequestParam(required = false, name = "subCID") String subCID,
            @RequestParam(required = false, name = "searchProduct") String searchProduct,
            @RequestParam(required = false, name = "orderOption") String orderOption,
            @RequestParam(required = false, name = "likeProduct") String likeProduct,
            @RequestParam(required = false, name = "URL") String URL,
            HttpSession session,
            Model model) {

//        정렬 방법 리스트 가져오기
        ArrayList<ProductSortDto> sortList =  productQueryService.getProductSortOptions();
        model.addAttribute("sortList", sortList);

        // 상품 전체 리스트 가져오기
        ArrayList<ProductDto> products;

        // 대분류 카테고리 가져오기
        ArrayList<ProductCategoryDto> list = productCategoryService.getMajorCategoryByPId();
        model.addAttribute("categoryList", list);

        // 중분류 카테고리 가져오기
        ArrayList<ProductCategoryDto> midCList = productCategoryService.getMiddleCategoryByPId(majorCID, "mid");
        model.addAttribute("midCategoryList", midCList);

        products = productQueryService.getFilteredProductData(majorCID);

        // 중분류 카테고리가 선택된 경우
        ArrayList<ProductCategoryDto> subCList;
        if(midCID != null) {
            subCList = productCategoryService.getMiddleCategoryByPId(midCID, "sub");
            model.addAttribute("subCategoryList", subCList);
            // 중분류 상품(소분류도 포함)만 가져오는 서비스
            products = productQueryService.getFilteredProductData(midCID);
        }

        // 소분류 카테고리가 선택된 경우
        if (subCID != null) {
            // 소분류 상품만 가져오는 서비스
            products = productQueryService.getFilteredProductData(subCID);
        }

//      카테고리 화면에서 검색하는 경우 -> 카테고리 필터링 & 그 상품의 이름으로 필터링 두번 다 진행
        if(searchProduct != null) {
            products = productQueryService.getProductBySearch(searchProduct, products);
        }

//        카테고리 화면에서 정렬하는 경우
        if(orderOption != null && !products.isEmpty()) {
            products = productQueryService.getCategoryProductWithOrderOption(products, orderOption);
        }

        String userId = Optional.ofNullable(session.getAttribute("customerId"))
                .map(Object::toString)
                .orElse(null);

        // 로그인이 되어 있을 경우
        if (userId != null) {
            // 현재 로그인된 유저가 좋아요 한 상품 표시하기
            ArrayList<String> likeInfo = productLikeService.getLikeProductById(userId);

            model.addAttribute("userLike", likeInfo);
        }

        // 좋아요를 누른 경우 -> js에서 이미 세션 체크를 거치므로 무조건 로그인이 되어있는 상태
        if (likeProduct != null){
            ProductLike productLike = new ProductLike();
            // 유저 아이디 가져오기 & 넣기
            productLike.setUserId(userId);
            // 상품 아이디 정보 넣기
            productLike.setProductId(likeProduct);

            // 서비스에서 좋아요를 등록
            productLikeService.setLikeProductById(productLike);
            return "redirect:/category"+URL.substring(0, URL.indexOf("&likeProduct"));
        }

        model.addAttribute("products", products);
        return "/product/category";
    }

    // 좋아요한 상품만 볼 수 있는 화면
    @GetMapping("/likeList")
    public String likeList(
            @RequestParam(required = false, name = "likeProduct") String likeProduct,
            @RequestParam(required = false, name = "URL") String URL,
            HttpSession session,
            Model model) {
        // 대분류 카테고리 가져오기
        ArrayList<ProductCategoryDto> list = productCategoryService.getMajorCategoryByPId();
        model.addAttribute("categoryList", list);

        // 유저 아이디 얻어옴
        String userId = Optional.ofNullable(session.getAttribute("customerId"))
                .map(Object::toString)
                .orElse(null);

        ArrayList<ProductDto> products = productLikeService.getLikeProductList(userId);
        model.addAttribute("products", products);

        // 로그인이 되어 있을 경우
        if (userId != null) {
            // 현재 로그인된 유저가 좋아요 한 상품 표시하기
            ArrayList<String> likeInfo = productLikeService.getLikeProductById(userId);

            model.addAttribute("userLike", likeInfo);
        }

        // 좋아요를 누른 경우 -> js에서 이미 세션 체크를 거치므로 무조건 로그인이 되어있는 상태
        if (likeProduct != null){
            ProductLike productLike = new ProductLike();
            // 유저 아이디 가져오기 & 넣기
            productLike.setUserId(userId);
            // 상품 아이디 정보 넣기
            productLike.setProductId(likeProduct);

            // 서비스에서 좋아요를 등록
            productLikeService.setLikeProductById(productLike);
            return "redirect:/likeList"+URL.substring(0, URL.indexOf("likeProduct"));
        }
        return "/product/likeProductList";
    }
}
