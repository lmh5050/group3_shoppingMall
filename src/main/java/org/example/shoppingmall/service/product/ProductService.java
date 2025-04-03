package org.example.shoppingmall.service.product;

import org.example.shoppingmall.dto.product.*;
import org.example.shoppingmall.repository.product.ProductDetailRepository;
import org.example.shoppingmall.repository.product.ProductLikeRepository;
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
//    private static final String UPLOAD_DIR = "C:\\JAVA\\fast_campus_KDT\\projects\\prj_02\\group3_shoppingMall\\src\\main\\resources\\static\\images\\product\\";
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/images/product/";
    private final ProductLikeRepository productLikeRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          ProductDetailRepository productDetailRepository,
                          ProductLikeRepository productLikeRepository) {
        this.productRepository = productRepository;
        this.productDetailRepository = productDetailRepository;
        this.productLikeRepository = productLikeRepository;
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

//    상세 정보를 중복 없이 가져옴
    public HashSet<String> getProductDetailOption(String productId, String option) {
        ArrayList<ProductDetailDto> productDtos = this.getProductDetailOptions(productId);
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

    // 기존 옵션에 존재하는 데이터인지 확인
//    private void setProductOptions(List<String> sizes, List<String> colors, String productId) {
        // 원래 기존에 존재하는 색상 * 사이즈는 그냥 DB에 냅두고, 새롭게 생기는 색상 * 사이즈만 추가하려고했는데
        // 잘 안되서 일단 관련된 모든 디테일 정보 삭제 후, 새로 insert하는 방향으로 가기로 결정
        // 일단 전처리까지는 잘 돌아가는데, DB에서 기존에 존재하는 컬럼 삭제가 잘 안됨
        //        // 기존에 존재하던 옵션들을 중복 제거
//        HashSet<String> existenceColor = this.getProductDetailOption(productId, "color");
//        HashSet<String> existenceSize = this.getProductDetailOption(productId, "size");
//
//        // 중복 제거 후, 각 사이즈와 컬러를 비교하여 이미 존재하는 애들만 남겨둠
//        Iterator<String> colorIterator = existenceColor.iterator();
//        while (colorIterator.hasNext()) {
//            if (!colors.contains(colorIterator.next())) {
//                colorIterator.remove();
//            }
//        }
//        Iterator<String> sizeIterator = existenceSize.iterator();
//        while (sizeIterator.hasNext()) {
//            if (!sizes.contains(sizeIterator.next())) {
//                sizeIterator.remove();
//            }
//        }
//
//        System.out.println("중복 제거 후, 이미 존재하는 애들을 출력");
//        System.out.println("existenceSize = " + existenceSize);
//        System.out.println("existenceColor = " + existenceColor);
//
//        // DB에서 이미 존재하는 애들을 제외한 나머지를 DB에서 삭제함
//        List<Map<String, String>> colorAndSize = new ArrayList<>();
//        for (String color : existenceColor) {
//            for (String size : existenceSize) {
//                Map<String, String> map = new HashMap<>();
//                map.put("color", color);
//                map.put("size", size);
//                colorAndSize.add(map);
//            }
//        }
//        Map<String, Object> pair = new HashMap<>();
//        pair.put("productId", productId);
//        pair.put("validColorSizePairs", colorAndSize); // List<Map<String, String>> 형태
//        productDetailRepository.removeByExcludedColorAndSize(pair);

        // detail 테이블에 컬럼 생성 -> 이미 존재하는 색상 * 컬러는 제외
//        this.updateDetailOptions(productId, colors, existenceColor, sizes, existenceSize);
//        this.updateDetailOptions(productId, colors, sizes);
//    }


    // 디테일 옵션 만들고 저장
//    private void updateDetailOptions(String productId, List<String> colors, HashSet<String> existenceColor, List<String> sizes, HashSet<String> existenceSize) {
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
        this.setDetailOptions(colors, sizes, productUpdateDto);;
    }

    // 디테일 옵션 만들고 저장
    private void setDetailOptions(List<String> colors, List<String> sizes, ProductUpdateDto productUpdateDto) {
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

    // 상품에 맞는 시즌 정보 가져오기
    public String getSeasonBySeasonId(int seasonId) {
        return productRepository.getSeasonBySeasonId(seasonId);
    }

    // 시즌 리스트 가져오기
    public ArrayList<ProductSeasonDTO> getAllSeasonList(){
        return productRepository.getAllSeasonList();
    }

    // 로그인된 유저가 상품 좋아요 리스트에 추가하기
    @Transactional
    public void setLikeProductById(ProductLike productLike) {
        // 기존에 좋아요 테이블에 존재하는지 확인
        ProductLike checkProductLike = this.checkLikeExists(productLike.getProductId(), productLike.getUserId());

        if (checkProductLike == null){ // 기존에 존재하지 않는 경우
            productLikeRepository.setLikeProductById(productLike);  //새로 등록
            productLikeRepository.updateProductLikeCountPlus(productLike.getProductId());  //상품 좋아요 수 ++
        } else { // 좋아요 취소
            productLikeRepository.deleteProductLike(productLike.getProductId());  //delete flag = 1오 만듬
            productLikeRepository.updateProductLikeCountMinus(productLike.getProductId());  //상품 좋아요 수 --
        }
    }

    // 로그인된 유저가 어떤 상품을 좋아요 눌렀는지 확인하기
    public ArrayList<String> getLikeProductById(String userId) {
        return productLikeRepository.getLikeProductById(userId);
    }

    // 상품을 좋아요 눌렀는지 확인하기(중복 확인을 위해)
    private ProductLike checkLikeExists(String productId, String userId) {
        return productLikeRepository.getCheckLikeExists(productId, userId);
    }

    // 좋아요한 상품만 dto 매핑하여 가져오기
    public ArrayList<ProductDto> getLikeProductList(String userId) {
        ArrayList<String> likeProductIds = this.getLikeProductById(userId);
        ArrayList<ProductDto> products = this.getProductData();
        ArrayList<ProductDto> likeProducts = new ArrayList<>();

        // 좋아요 한 상품만 필터링
        for(ProductDto productDto : products){
            for(String likeProductId : likeProductIds){
                if(productDto.getProductId().equals(likeProductId)){
                    likeProducts.add(productDto);
                }
            }
        }

        return likeProducts;
    }
}
