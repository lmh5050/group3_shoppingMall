package org.example.shoppingmall.service.product;

import org.example.shoppingmall.dto.product.ProductDescriptionImageDto;
import org.example.shoppingmall.dto.product.ProductDetailDto;
import org.example.shoppingmall.dto.product.ProductUpdateDto;
import org.example.shoppingmall.repository.product.ProductDetailRepository;
import org.example.shoppingmall.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

// 상품 상태 및 상세 정보 관리
@Service
public class ProductManagementService {
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductQueryService productQueryService;
//    private final ProductUploadService productUploadService;
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/images/product/";

    @Autowired
    public ProductManagementService(
            ProductRepository productRepository,
            ProductDetailRepository productDetailRepository,
            ProductQueryService productQueryService
//    ProductUploadService productUploadService
    ){
        this.productRepository = productRepository;
        this.productDetailRepository = productDetailRepository;
        this.productQueryService = productQueryService;
//        this.productUploadService = productUploadService;
    }

    // 상품의 상태 변경
    public void setProductStatus(String productId, String status) {
        // 유효성 검사
        if(!isValidStatus(productId, status)){
            return;
        }
        productRepository.updateProductStatus(productId, status);
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

    // 변경사항 저장하기
    public String updateProductInfo(ProductUpdateDto productUpdateDto) {
        // 컬러와 사이즈 전처리
        productUpdateDto = this.preprocessingOptions(productUpdateDto);

        // 유효성 검사
        if (productUpdateDto == null){
            return "입력하신 Color와 Size를 확인해 주세요.";
        }

        // 기존의 상품 상세 정보 삭제
//        productDetailRepository.removeProductDetailsByProductId(productUpdateDto.getProductId());

        // 상품 상세 정보 DB에 저장
//        this.setProductOptions(productUpdateDto.getSizes(), productUpdateDto.getColors(), productUpdateDto.getProductId());
//        this.updateDetailOptions(productUpdateDto.getProductId(), productUpdateDto.getColors(), productUpdateDto.getSizes());

        // 상품 DB에 업데이트
        productRepository.updateProductInfo(productUpdateDto);
        return null;
    }

    // DB에 업데이트 전 option들 전처리
    private ProductUpdateDto preprocessingOptions(ProductUpdateDto productUpdateDto) {
        if(productUpdateDto == null){
            return null;
        }

        // size 전처리 (check && 빈값이 아닌 경우)
        List<String> sizes = this.colorAndSizeCheck(productUpdateDto.getSizes() , productUpdateDto.getSizeStatus());

        // color 전처리 (check && 빈값이 아닌 경우)
        List<String> colors = this.colorAndSizeCheck(productUpdateDto.getColors() ,productUpdateDto.getColorStatus());

        // newSize 전처리 (빈값이 아닌 경우)
        List<String> newSizes = productUpdateDto.getNewSizes();
        if(newSizes == null || newSizes.isEmpty()){
            newSizes = new ArrayList<>();
            productUpdateDto.setNewSizes(newSizes);
        }
        newSizes.removeIf(color -> color.trim().isEmpty());

        // newColor 전처리 (빈값이 아닌 경우)
        List<String> newColors = productUpdateDto.getNewColors();
        if(newColors == null || newColors.isEmpty()){
            newColors = new ArrayList<>();
            productUpdateDto.setNewColors(newColors);
        }
        newColors.removeIf(color -> color.trim().isEmpty());

        // 총 개수
        int totalSizesCount = sizes.size() + newSizes.size();
        int totalColorsCount = colors.size() + newColors.size();

        // 1. 새로운 옵션(컬러 || 사이즈)이 없는 경우
        if (totalSizesCount == 0 && totalColorsCount == 0) {
            return null;
        }

        // 색상/사이즈가 업데이트 되기 위해 기존에 존재하던 옵션들은 status를 0으로 만들고, 나머지를 1로 새로 등록
        // 기존에 존재하던 옵션 status를 0으로
        productDetailRepository.updateProductDetailsStatusByZero(productUpdateDto.getProductId());
        sizes.addAll(newSizes);
        colors.addAll(newColors);
        List<String> defaultList = new ArrayList<>();

        // 2. 색상을 업데이트
        if (totalColorsCount > 0 && totalSizesCount > 0){  // 색상과 사이즈 모두 존재 -> 카타시안 곱 등록
            this.setDetailOptions(sizes, colors, productUpdateDto);
        } else if (totalColorsCount > 0 && totalSizesCount == 0){  //원 사이즈 등록: 색상만 바뀜
            defaultList.add("FREE");
            this.setDetailOptions(defaultList, colors, productUpdateDto);
        } else if (totalColorsCount == 0 && totalSizesCount > 0){  //원 컬러 등록: 사이즈만 바뀜
            defaultList.add("");
            this.setDetailOptions(sizes, defaultList, productUpdateDto);
        }

        return productUpdateDto;
    }

    // list에서 checked 된 데이터만 사용하기 위해 필터링
    private List<String> colorAndSizeCheck(List<String> list, ArrayList<Boolean> status) {
        if(list == null || list.isEmpty()){
            return new  ArrayList<>();
        }

        // 그냥 remove를 사용하면 인덱스가 밀려서 삭제가 안됨 -> Iterator 사용
        Iterator<String> listIterator = list.iterator();
        Iterator<Boolean> statusIterator = status.iterator();

        while (listIterator.hasNext() && statusIterator.hasNext()) {
            listIterator.next(); // 리스트 요소 가져오기
            if (statusIterator.next()) { // status 값 확인
                listIterator.remove();
                statusIterator.remove();
            }
        }

        // 빈 값은 checked 되어있어도 사용하지 않음
        list.removeIf(color -> color.trim().isEmpty());

        return list;
    }

    private void updateDetailOptions(String productId, List<String> colors, List<String> sizes) {
        ArrayList<ProductDetailDto> productDetailDtos = new ArrayList<>();

        // 상품 상세 아이디 생성
        String productDetailId = this.makeProductId("productDetailId", null);
        for(String color : colors){
            for(String size : sizes){
                ProductDetailDto productDetailDto = new ProductDetailDto();
                // 상품 상세 정보 DTO에 저장
                productDetailDto.setProductId(productId);  //상품 아이디
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

    //    상세 정보를 중복 없이 가져옴
    public HashSet<String> getProductDetailOption(String productId, String option) {
        ArrayList<ProductDetailDto> productDtos = productQueryService.getProductDetailOptions(productId);
        HashSet<String> filteredProducts = new HashSet<>();

        if(option.equals("color")){  // 컬러 옵션
            for(ProductDetailDto productDetail : productDtos){
                if(!productDetail.getStatus().equals("0")){
                    filteredProducts.add(productDetail.getColor());
                }
            }
        }else if(option.equals("size")){  // 사이즈 옵션
            for(ProductDetailDto productDetail : productDtos){
                if(!productDetail.getStatus().equals("0")) {
                    filteredProducts.add(productDetail.getSize());
                }
            }
        }

        return filteredProducts;
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

            // 파일 저장이 완료될 때까지 대기 (최대 3초)
            int retries = 0;
            while (!destinationFile.exists() && retries < 30) {
                Thread.sleep(100); // 0.1초씩 기다리면서 파일 존재 확인
                retries++;
            }

            // 새로운 상품 DB에 등록
            productUpdateDto.setProductId(productId);
            productUpdateDto.setColorCount(productUpdateDto.getColors().size());
            productUpdateDto.setSizeCount(productUpdateDto.getColors().size());
            ProductDescriptionImageDto productDescriptionImageDto = new ProductDescriptionImageDto();

            // 상품 사진 DTO 에 등록order_detail
            productDescriptionImageDto.setProductId(productId);
            productDescriptionImageDto.setForm(imgForm);

            this.setNewProduct(productUpdateDto, productDescriptionImageDto);
            // 잘못 입력되었다는 정보를 가지고 본페이지로 redirect
            return "성공";
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "파일 저장 오류";
        }
    }

    // 상품을 추가하기
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

        // DB에 저장
        this.setDetailOptions(colors, sizes, productUpdateDto);
    }

    // 디테일 옵션 만들고 저장
    void setDetailOptions(List<String> colors, List<String> sizes, ProductUpdateDto productUpdateDto) {
        // 반복문으로 insert문을 통해 등록
        ArrayList<ProductDetailDto> productDetailDtos = new ArrayList<>();

        // 상품 상세 아이디 생성
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
}
