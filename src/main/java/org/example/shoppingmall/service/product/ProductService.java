package org.example.shoppingmall.service.product;

import org.example.shoppingmall.dto.User.UserInfoDto;
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
}

