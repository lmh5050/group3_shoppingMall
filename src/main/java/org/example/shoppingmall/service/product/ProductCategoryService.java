package org.example.shoppingmall.service.product;

import org.example.shoppingmall.dto.product.ProductCategoryDto;
import org.example.shoppingmall.repository.product.ProductCategoryRepository;
import org.example.shoppingmall.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

//
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

    // 대분류 불러오기
    public ArrayList<ProductCategoryDto> getMajorCategoryByPId() {
        return productCategoryRepository.getMajorCategoryByPId();
    }

    // 분류에 맞는 카테고리만 가지고 오기
    public ArrayList<ProductCategoryDto> getMiddleCategoryByPId(String PCID, String role) {  //찾는 대분류 ID를 매개변수로 받음
        // 1. 먼저 들어온 부모 카테고리 아이디가 유효한지 판단
        if (!isCategory(PCID, role)) {
            return null;
        }

        // 2. 대분류를 제외한 중분류만 가져오기
        return productCategoryRepository.getMiddleCategoryById(PCID);
    }

    // 카테고리가 아닌 경우 확인
    private boolean isCategory(String PCID, String role) {
       if(role.equals("mid")){
           return isMajorCategory(PCID);
       } else if(role.equals("sub")) {
           return isMidCategory(PCID);
       } else {
           return false;
       }
    }

    // 대분류가 아닌 경우를 반환하는 메서드
    private boolean isMajorCategory(String PCID) {
        for(ProductCategoryDto ctg : getMajorCategoryByPId()) {
            if(ctg.getCategoryId().equals(PCID) && ctg.getParentCategoryId() == null){
                return true;
            }
        }
        return false;
    }

    // 중분류에 맞는 소분류만 불러오기
    private boolean isMidCategory(String PCID) {
        for(ProductCategoryDto ctg : getCategoryListAll()) {
            if(ctg.getCategoryId().contains(PCID)){
                return true;
            }
        }
        return false;
    }

    // 소분류만 가져오기
    public ArrayList<ProductCategoryDto> getSubCategory() {
        ArrayList<ProductCategoryDto> categorys = this.getCategoryListAll();
        ArrayList<ProductCategoryDto> subCategory = new ArrayList<>();

        for(ProductCategoryDto ctg : categorys) {
            if(ctg.getCategoryId().contains("REF"))
                continue;

            String[] tmp = ctg.getCategoryId().split("_");

            if(tmp.length <= 2){
                continue;
            }

            if(ctg.getCategoryId().contains("ALL"))
                ctg.setName("전체 - "+ctg.getName());
            else if(ctg.getCategoryId().contains("MAN"))
                ctg.setName("남성 - "+ctg.getName());
            else if(ctg.getCategoryId().contains("FEM"))
                ctg.setName("여성 - "+ctg.getName());

            subCategory.add(ctg);
        }
        return subCategory;
    }
}
