package org.example.shoppingmall.service.product;

import org.example.shoppingmall.dto.product.*;
import org.example.shoppingmall.repository.product.ProductDetailRepository;
import org.example.shoppingmall.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;


@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private static final String UPLOAD_DIR = "C:\\JAVA\\fast_campus_KDT\\projects\\prj_02\\group3_shoppingMall\\src\\main\\resources\\static\\images\\product\\";

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

    // 상품 등록
    @Transactional
    public String productUpload(MultipartFile imageFile, ProductUpdateDto productUpdateDto) {
        // 파일이 없는 경우 메세지 전달
        if (imageFile == null) {
            return "파일이 없습니다.";
        }
        try {

            // 상품 번호 - 현재 등록된 상품 중 마지막 + 1
            String productId = this.makeProductId("productId", null);
            // 파일 저장 경로 설정
            String imgForm = ".png";
            File destinationFile = new File(UPLOAD_DIR + productId + imgForm); // 파일 이름은 productId.jpg로 설정

            // 폴더 없으면 생성
            destinationFile.getParentFile().mkdirs();

            // 파일 저장
            imageFile.transferTo(destinationFile);

            // 새로운 상품 DB에 등록
            productUpdateDto.setProductId(productId);
            productUpdateDto.setColorCount(productUpdateDto.getColors().size());
            productUpdateDto.setSizeCount(productUpdateDto.getColors().size());
            ProductDescriptionImageDto productDescriptionImageDto = new ProductDescriptionImageDto();

            // 상품 사진 DTO 에 등록
            productDescriptionImageDto.setProductId(productId);
            productDescriptionImageDto.setForm(imgForm);

            this.setNewProduct(productUpdateDto, productDescriptionImageDto);
            // 잘못 입력되었다는 정보를 가지고 본페이지로 redirect
            return "성공";
        } catch (IOException e) {
            e.printStackTrace();
            return "파일 저장 오류";
        }
    }


    // 상품을 추가하기
    public void setNewProduct(ProductUpdateDto productUpdateDto, ProductDescriptionImageDto productDescriptionImageDto) {
        productRepository.setNewProduct(productUpdateDto);
        this.setProductImage(productDescriptionImageDto);
        this.setProductDetailByColorsAndSizes(productUpdateDto);

        System.out.println("(완료) productUpdateDto = " + productUpdateDto);
    }

    // 들어온 상품 정보의 유효성 검사
//    private String validNewProductInfo(ProductUpdateDto productDto) {
//        // 상품 사진
//        if (productDto.getImage() == null){
//            return "상품 사진이 비어있습니다.";
//        }
//
//        // 상품 이름
//        if (productDto.getProductName() == null || productDto.getProductName().isEmpty()){
//            return "상품 이름이 비어있습니다";
//        } else if (productDto.getProductName().length() < 5) {
//            return "상품 이름은 5글자 이상으로 등록되어야 합니다.";
//        } else if  (productDto.getProductName().length() > 50) {
//            return "상품 이름은 50자 이하로 등록외어야 합니다.";
//        }
//
//        // 상품 가격 -> 핸들러로 처리
//        // 상품 상세 설명
//        if (productDto.getProductDescription() == null || productDto.getProductDescription().isEmpty()){
//            return "상품 상세 설명이 비어있습니다";
//        } else if (productDto.getProductDescription().length() < 20) {
//            return "상품 상세 설명은 20자 이상이어야 합니다.";
//        } else if  (productDto.getProductDescription().length() > 100) {
//            return "상품 상세 설명은 100자 이하이어야 합니다.";
//        }
//
//        // 카테고리
//        if (productDto.getProductCategoryId().equals("default")){
//            return "상품의 카테고리는 필수로 선택되어야 합니다.";
//        }
//
//        // 시즌
//        if (productDto.getProductSeasonId().equals("default")){
//            return "상품의 시즌은 필수로 선택되어야 합니다.";
//        }
//
//        // 비고
//        if (productDto.getProductNotes().length() >255){
//            return "상품 비고의 길이는 255자 이하어야 합니다.";
//        }
//
//        // 성별
//        if (productDto.getProductGender().equals("default")){
//            return "상품의 성별은 필수로 선택되어야 합니다.";
//        }
//
//        // 핏
//        if (productDto.getProductFit().equals("default")){
//            return "상품의 핏은 필수로 선택되어야 합니다.";
//        }
//
//        // 촉감
//        if (productDto.getProductTexture().equals("default")){
//            return "상품의 촉감은 필수로 선택되어야 합니다.";
//        }
//
//        // 두께
//        if (productDto.getProductThickness().equals("default")){
//            return "상품의 두께는 필수로 선택되어야 합니다.";
//        }
//
//        // 제조사
//        if (productDto.getProductManufacturer() != null || productDto.getProductManufacturer().isEmpty()){
//            return "상품의 제조사는 필수로 선택되어야 합니다.";
//        }
//
//        // 원산지
//        if (productDto.getProductOrigin() != null || productDto.getProductOrigin().isEmpty()){
//            return "상품의 원산지는 필수로 선택되어야 합니다.";
//        }
//
//        // 품질 보증 기준
//        if (productDto.getProductQualityAssuranceStandard() != null || productDto.getProductQualityAssuranceStandard().isEmpty()){
//            return "상품의 품질 보증 기준은 필수로 선택되어야 합니다.";
//        }
//
//        // 색상
//        if (productDto.getColors().isEmpty()){
//            return "상품의 색상은 필수로 작성되어야 합니다.";
//        }
//
//        // 사이즈
//        if (productDto.getSizes().isEmpty()){
//            return "상품의 사이즈는 필수로 작성되어야 합니다.";
//        }
//
//        return null;
//    }

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

    // 시즌 리스트 가져오기
    public ArrayList<ProductSeasonDTO> getAllSeasonList(){
        return productRepository.getAllSeasonList();
    }
}
