package org.example.shoppingmall.service.product;

import org.example.shoppingmall.dto.product.ProductCategoryDto;
import org.example.shoppingmall.repository.product.ProductCategoryRepository;
import org.example.shoppingmall.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryService(ProductCategoryRepository pCategoryRepo) {
        this.productCategoryRepository = pCategoryRepo;
    }

    public ArrayList<ProductCategoryDto> getCategoryListAll() {
        return productCategoryRepository.getCategoryListAll();
    }

    public ArrayList<ProductCategoryDto> getCategoryByPId(String categoryId) {
        return productCategoryRepository.getCategoryByPId(categoryId);
    }

    // 대분류 불러오기
    public ArrayList<ProductCategoryDto> getMajorCategoryByPId() {
        return productCategoryRepository.getMajorCategoryByPId();
    }

    // 대분류에 맞는 중분류만 불러오기
    public ArrayList<ProductCategoryDto> getMiddleCategoryByPId() {
        return null;
    }

    // 중분류에 맞는 소분류만 불러오기


}
