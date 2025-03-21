package org.example.shoppingmall.service.product;

import org.example.shoppingmall.dto.User.UserInfoDto;
import org.example.shoppingmall.dto.product.ProductDetailDto;
import org.example.shoppingmall.dto.product.ProductDto;
import org.example.shoppingmall.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
}



