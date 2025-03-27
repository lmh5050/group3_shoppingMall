package org.example.shoppingmall.service.product;

import org.example.shoppingmall.dto.User.UserInfoDto;
import org.example.shoppingmall.dto.product.*;
import org.example.shoppingmall.repository.product.ProductDetailRepository;
import org.example.shoppingmall.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // 새로운 상품 등록 시, 현재 마지막 상품번호 + 1
    public String makeProductId(String role, String init) {
        String lastProductId;
        int tmp;
        int zeroLen;
        String prefix;

        if(role.equals("productId")){ // 상품 번호를 만들 경우
            lastProductId = productRepository.getLastProductId();
            tmp = Integer.parseInt(lastProductId.substring(1)) + 1;
            zeroLen = lastProductId.length() - 1 - String.valueOf(tmp).length();
            prefix = "P";
        } else if (role.equals("productDetailId")) {  //상품 상세 번호를 만들 경우
            if (init == null){
                lastProductId = productDetailRepository.getLastProductDetailId();
            } else {
                lastProductId = init;
            }
            tmp = Integer.parseInt(lastProductId.substring(2)) + 1;
            zeroLen = lastProductId.length() - 2 - String.valueOf(tmp).length();
            prefix = "PD";
        } else {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        sb.append(prefix);
        sb.append("0".repeat(Math.max(0, zeroLen)));
        sb.append(tmp);
        return sb.toString();
    }

    // 상품을 추가하기
    @Transactional
    public void setNewProduct(ProductUpdateDto productUpdateDto, ProductDescriptionImageDto productDescriptionImageDto) {
        productRepository.setNewProduct(productUpdateDto);
        this.setProductImage(productDescriptionImageDto);
        this.setProductDetailByColorsAndSizes(productUpdateDto);
    }

    // 상품 사진 테이블에 등록하기
    private void setProductImage(ProductDescriptionImageDto productDescriptionImageDto) {
        productRepository.setProductImage(productDescriptionImageDto);
    }

    // 상품에 등록된 옵션을 카타시안 곱을 통해 매칭해주고 DB에 저장하기
    private void setProductDetailByColorsAndSizes(ProductUpdateDto productUpdateDto) {
        // 등록된 컬러/사이즈를 가져옴
        List<String> colors = productUpdateDto.getColors();
        List<String> sizes = productUpdateDto.getSizes();

        // 반복문으로 insert문을 통해 등록
        ArrayList<ProductDetailDto> productDetailDtos = new ArrayList<>();
        String productDetailId = this.makeProductId("productDetailId", null);
        for(String color : colors){
            for(String size : sizes){
                ProductDetailDto productDetailDto = new ProductDetailDto();
                // 상품 상세 정보 DTO에 저장
                productDetailDto.setProductId(productUpdateDto.getProductId());  //상품 아이디
                productDetailDto.setProductDetailId(productDetailId);  //상품 상세 아이디
                productDetailDto.setColor(color);  //상품 컬러
                productDetailDto.setSize(size);  //상품 사이즈
                productDetailDtos.add(productDetailDto);  //리스트에 추가
                productDetailId = this.makeProductId("productDetailId", productDetailId);
            }
        }

        // DB에 저장
        for(ProductDetailDto productDetailDto : productDetailDtos){
            productDetailRepository.setProductDetailByColorsAndSizes(productDetailDto);
        }
    }

    // 상품에 맞는 시즌 정보 가져오기
    public String getSeasonBySeasonId(int seasonId) {
        return productRepository.getSeasonBySeasonId(seasonId);
    }
}
