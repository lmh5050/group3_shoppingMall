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
import java.util.List;

// 상품 업로드 및 이미지 처리
@Service
public class ProductUploadService {
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductManagementService productManagementService;;
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/images/product/";


    @Autowired
    public ProductUploadService(
            ProductRepository productRepository,
            ProductDetailRepository productDetailRepository,
            ProductManagementService productManagementService) {
        this.productRepository = productRepository;
        this.productDetailRepository = productDetailRepository;
        this.productManagementService = productManagementService;
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
            String productId = productManagementService.makeProductId("productId", null);
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
        this.setDetailOptions(colors, sizes, productUpdateDto);;
    }

    // 디테일 옵션 만들고 저장
    private void setDetailOptions(List<String> colors, List<String> sizes, ProductUpdateDto productUpdateDto) {
        // 반복문으로 insert문을 통해 등록
        ArrayList<ProductDetailDto> productDetailDtos = new ArrayList<>();

        // 상품 상세 아이디 생성
        String productDetailId = productManagementService.makeProductId("productDetailId", null);
        for(String color : colors){
            for(String size : sizes){
                ProductDetailDto productDetailDto = new ProductDetailDto();
                // 상품 상세 정보 DTO에 저장
                productDetailDto.setProductId(productUpdateDto.getProductId());  //상품 아이디
                productDetailDto.setProductDetailId(productDetailId);  //상품 상세 아이디
                productDetailDto.setColor(color);  //상품 컬러
                productDetailDto.setSize(size);  //상품 사이즈
                productDetailDtos.add(productDetailDto);  //리스트에 추가
                productDetailId = productManagementService.makeProductId("productDetailId", productDetailId);
            }
        }

        // DB에 저장
        for(ProductDetailDto productDetailDto : productDetailDtos){
            productDetailRepository.setProductDetailByColorsAndSizes(productDetailDto);
        }
    }

}
