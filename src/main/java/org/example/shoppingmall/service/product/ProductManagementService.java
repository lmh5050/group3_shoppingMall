package org.example.shoppingmall.service.product;

import org.example.shoppingmall.dto.product.ProductDetailDto;
import org.example.shoppingmall.dto.product.ProductUpdateDto;
import org.example.shoppingmall.repository.product.ProductDetailRepository;
import org.example.shoppingmall.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductManagementService {
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductQueryService productQueryService;

    @Autowired
    public ProductManagementService(
            ProductRepository productRepository,
            ProductDetailRepository productDetailRepository,
            ProductQueryService productQueryService) {
        this.productRepository = productRepository;
        this.productDetailRepository = productDetailRepository;
        this.productQueryService = productQueryService;
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
        productDetailRepository.removeProductDetailsByProductId(productUpdateDto.getProductId());

        // 상품 상세 정보 DB에 저장
//        this.setProductOptions(productUpdateDto.getSizes(), productUpdateDto.getColors(), productUpdateDto.getProductId());
        this.updateDetailOptions(productUpdateDto.getProductId(), productUpdateDto.getColors(), productUpdateDto.getSizes());

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

        // 입력한 값이 하나도 없는 경우
        if (sizes == null || sizes.isEmpty() || colors == null || colors.isEmpty()) {
            return null;
        }
        // DTO에 전처리된 데이터 저장
        productUpdateDto.setSizes(sizes);
        productUpdateDto.setColors(colors);

        return productUpdateDto;
    }

    // list에서 checked 된 데이터만 사용하기 위해 필터링
    private List<String> colorAndSizeCheck(List<String> list, ArrayList<Boolean> status) {
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
                // 만약 이미 존재하는 색상 * 사이즈인 경우에는 넘어감
//                if(existenceColor.contains(color) && existenceSize.contains(size)){
//                    continue;
//                }
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
                filteredProducts.add(productDetail.getColor());
            }
        }else if(option.equals("size")){  // 사이즈 옵션
            for(ProductDetailDto productDetail : productDtos){
                filteredProducts.add(productDetail.getSize());
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
}
