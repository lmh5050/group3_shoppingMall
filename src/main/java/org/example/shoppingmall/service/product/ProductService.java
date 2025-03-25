package org.example.shoppingmall.service.product;

import org.example.shoppingmall.dto.User.UserInfoDto;
import org.example.shoppingmall.dto.product.*;
import org.example.shoppingmall.repository.product.ProductDetailRepository;
import org.example.shoppingmall.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,  ProductDetailRepository productDetailRepository) {
        this.productRepository = productRepository;
        this.productDetailRepository = productDetailRepository;
    }

    // 모든 상품 리스트를 가져옴
    public ArrayList<ProductDto> getProductData() {
        return productRepository.getProductData();
    }

    // 상품 아이디를 가지고 상품 한 개를 가져옴
    public ProductDto getProductById(String productId) {
        return productRepository.getProductById(productId);
    }

    // 필터링된 상품만 가져옴
    public ArrayList<ProductDto> getFilteredProductData(String category) {
        ArrayList<ProductDto> products = this.getProductData();
        ArrayList<ProductDto> filteredProducts = new ArrayList<>();

        // 전체인 경우
        if(category.contains("ALL")){
            category = category.replace("ALL", "");
        }

        for (ProductDto product : products) {
            if(product.getCategoryId().contains(category))
                filteredProducts.add(product);
        }
        return filteredProducts;
    }

//    상품의 상세 정보를 조회함
    public ArrayList<ProductDetailDto> getProductDetailOptions(String productId) {
        return productRepository.getProductDetailOptions(productId);
    }

//    상품을 원하는 순서로 정렬
    public ArrayList<ProductDto> getProductOrderByOptions(String orderOption) {
        return productRepository.getProductOrderByOptions(orderOption);
    }

//    카테고리에서 이름으로 찾을 경우 -> 카테고리 & 이름으로 필터링
    public  ArrayList<ProductDto> getProductBySearch(String search, ArrayList<ProductDto> products) {
        ArrayList<ProductDto> searchProducts = productRepository.getProductBySearch(search);
        ArrayList<ProductDto> filteredProducts = new ArrayList<>();
        System.out.println("searchProducts = " + searchProducts);
        System.out.println("products = " + products);
        for(ProductDto categoryProduct : products){
            for(ProductDto searchProduct : searchProducts){
                if(categoryProduct.getName().contains(searchProduct.getName())){
                    filteredProducts.add(categoryProduct);
                }
            }
        }
        System.out.println("filteredProducts = " + filteredProducts);
        return filteredProducts;
    }

//    정렬 리스트 가져오기
    public  ArrayList<ProductSortDto> getProductSortOptions() {
        return productRepository.getProductSortOptions();
    }

    //    카테고리에서 상품 정렬하기
    public ArrayList<ProductDto> getCategoryProductWithOrderOption(ArrayList<ProductDto> productDtos, String orderOprion) {
        return productRepository.getCategoryProductWithOrderOption(this.getProductIdList(productDtos), orderOprion);
    }
    
    // 상품의 아이디만 뽑기
    private ArrayList<String> getProductIdList(ArrayList<ProductDto> productDto) {
        ArrayList<String> productIdList = new ArrayList<>();
        for(ProductDto product : productDto){
            productIdList.add(product.getProductId());
        }
        return productIdList;
    }

    // 상품의 상태 변경
    public void setProductStatus(String productId, String status) {
        // 유효성 검사
        if(!isValidStatus(productId, status)){
            return;
        }
        productRepository.setProductStatus(productId, status);
    }

    private boolean isValidStatus(String productId, String status) {
        if(status == null || status.equals("")){
            return false;
        }
        return switch (status) {
            case "display", "stop-display", "sold-out" -> true;
            default -> false;
        };
    }

//    상세 정보를 중복 없이 가져옴
    public HashSet<String> getProductDetailOption(String productId, String option) {
        ArrayList<ProductDetailDto> productDtos = this.getProductDetailOptions(productId);
        HashSet<String> filteredProducts = new HashSet<>();

        if(option.equals("color")){
            for(ProductDetailDto productDetail : productDtos){
                filteredProducts.add(productDetail.getColor());
            }
        }else if(option.equals("size")){
            for(ProductDetailDto productDetail : productDtos){
                filteredProducts.add(productDetail.getSize());
            }
        }

        return filteredProducts;
    }

    // 변경사항 저장하기
    public void setProductInfo(ProductUpdateDto productUpdateDto) {
        productRepository.setProductInfo(productUpdateDto);
    }
}



