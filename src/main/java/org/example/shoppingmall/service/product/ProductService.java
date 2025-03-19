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
    //
    public ArrayList<ProductDto> getProductData() {
        return productRepository.getProductData();
    }
    public ProductDto getProductById(String productId) {
        return productRepository.getProductById(productId);
    }
}

