package org.example.shoppingmall.service.product;

import org.assertj.core.api.Assertions;
import org.example.shoppingmall.dto.product.ProductDetailDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    void getProductById() {
        String lastProductId = "P00020";

        int tmp = Integer.parseInt(lastProductId.substring(1)) + 1;
        int zeroLen = lastProductId.length() - 1 - String.valueOf(tmp).length();

        StringBuffer sb = new StringBuffer();
        sb.append("P");
        sb.append("0".repeat(Math.max(0, zeroLen)));
        sb.append(tmp);
        System.out.println("sb = " + sb);
    }

    @Test
    void makeProductId(){
        String role = "productId";
        String res = "";
        String lastProductId;
        int tmp = 0;
        int zeroLen = 0;
        String prefix = "";

        if(role.equals("productId")){ // 상품 번호를 만들 경우
            lastProductId = "P00020";
            tmp = Integer.parseInt(lastProductId.substring(1)) + 1;
            zeroLen = lastProductId.length() - 1 - String.valueOf(tmp).length();
            prefix = "P";
        } else if (role.equals("productDetailId")) {  //상품 상세 번호를 만들 경우
            lastProductId = "PD00020";
            tmp = Integer.parseInt(lastProductId.substring(2)) + 1;
            zeroLen = lastProductId.length() - 1 - String.valueOf(tmp).length();
            prefix = "PD";
        }

        StringBuffer sb = new StringBuffer();
        sb.append(prefix);
        sb.append("0".repeat(Math.max(0, zeroLen)));
        sb.append(tmp);
        res = sb.toString();
        System.out.println("res = " + res);
        Assertions.assertThat(res.equals("P00021")).isTrue();
    }

    @Test
    void makeProductId2(){
        String role = "productDetailId";
        String res = "";
        String lastProductId;
        int tmp = 0;
        int zeroLen = 0;
        String prefix = "";

        if(role.equals("productId")){ // 상품 번호를 만들 경우
            lastProductId = "P00020";
            tmp = Integer.parseInt(lastProductId.substring(1)) + 1;
            zeroLen = lastProductId.length() - 1 - String.valueOf(tmp).length();
            prefix = "P";
        } else if (role.equals("productDetailId")) {  //상품 상세 번호를 만들 경우
            lastProductId = "PD00020";
            tmp = Integer.parseInt(lastProductId.substring(2)) + 1;
            zeroLen = lastProductId.length() - 2 - String.valueOf(tmp).length();
            prefix = "PD";
        }

        StringBuffer sb = new StringBuffer();
        sb.append(prefix);
        sb.append("0".repeat(Math.max(0, zeroLen)));
        sb.append(tmp);
        res = sb.toString();
        System.out.println("res = " + res);
        Assertions.assertThat(res.equals("PD00021")).isTrue();
    }

    @Test
    void updateDetailOptions(){
        // given
        String productId = "P00020";
        List<String> colors =  new ArrayList<>();
        List<String> sizes =  new ArrayList<>();
        colors.add("red");
        colors.add("black");
        sizes.add("M");
        sizes.add("L");
        sizes.add("s");

        // 기존에 존재하던 옵션들을 중복 제거
        HashSet<String> existenceColor = new HashSet<>();
        HashSet<String> existenceSize = new HashSet<>();
        existenceColor.add("red");
        existenceColor.add("green");
        existenceColor.add("blue");
        existenceSize.add("s");
        existenceSize.add("m");
        existenceSize.add("l");

        // when

        // 중복 제거 후, 각 사이즈와 컬러를 비교하여 이미 존재하는 애들만 남겨둠
        existenceColor.removeIf(c -> !colors.contains(c));
        existenceSize.removeIf(s -> !sizes.contains(s));

        System.out.println("existenceColor = " + existenceColor);
        System.out.println("existenceSize = " + existenceSize);

        // DB에서 이미 존재하는 애들을 제외한 나머지를 삭제함
        /* <delete id="removeByColorAndSize">
        DELETE FROM product_detail
        WHERE product_id = #{productId}
        AND (color != #{color} OR size != #{size})
    </delete>
        * */

        // detail 테이블에 컬럼 생성 -> 이미 존재하는 색상 * 컬러는 제외
        this.updateDetailOptions(productId, colors, existenceColor, sizes, existenceSize);
        // then
    }

    // 디테일 옵션 만들고 저장
    private void updateDetailOptions(String productId, List<String> colors, HashSet<String> existenceColor, List<String> sizes, HashSet<String> existenceSize) {
        ArrayList<ProductDetailDto> productDetailDtos = new ArrayList<>();

        // 상품 상세 아이디 생성
        int productDetailId = 1;
        // 이미 존재하는 경우를 제외하는 방법
        for(String color : colors){
            for(String size : sizes){
                // 만약 이미 존재하는 색상 * 사이즈인 경우에는 넘어감
                if(existenceColor.contains(color) && existenceSize.contains(size)){
                    continue;
                }
                ProductDetailDto productDetailDto = new ProductDetailDto();
                // 상품 상세 정보 DTO에 저장
                productDetailDto.setProductId(productId);  //상품 아이디
                productDetailDto.setProductDetailId(String.valueOf(productDetailId));  //상품 상세 아이디
                productDetailDto.setColor(color);  //상품 컬러
                productDetailDto.setSize(size);  //상품 사이즈
                productDetailDtos.add(productDetailDto);  //리스트에 추가
                productDetailId +=1;
            }
        }

        // 모두 만드는 경우
        ArrayList<ProductDetailDto> productDetailDtos2 = new ArrayList<>();
        for(String color : colors){
            for(String size : sizes){
                ProductDetailDto productDetailDto = new ProductDetailDto();
                // 상품 상세 정보 DTO에 저장
                productDetailDto.setProductId(productId);  //상품 아이디
                productDetailDto.setProductDetailId(String.valueOf(productDetailId));  //상품 상세 아이디
                productDetailDto.setColor(color);  //상품 컬러
                productDetailDto.setSize(size);  //상품 사이즈
                productDetailDtos2.add(productDetailDto);  //리스트에 추가
                productDetailId +=1;
            }
        }
        // DB에 저장
        for(ProductDetailDto productDetailDto : productDetailDtos2){
            System.out.println(productDetailDto.getColor() + ":" + productDetailDto.getSize() );
        }
        System.out.println();
        for(ProductDetailDto productDetailDto : productDetailDtos){
            System.out.println(productDetailDto.getColor() + ":" + productDetailDto.getSize());
        }
    }
    /*
    existenceColor = [red]
    existenceSize = [s]

    red:M
    red:L
    red:s
    black:M
    black:L
    black:s

    red:M
    red:L
    black:M
    black:L
    black:s
    * */

    private List<String> colorAndSizeCheck(List<String> list, ArrayList<Boolean> status) {
        for (int i=0; i < status.size(); i++) {
            if(!status.get(i)){
                list.remove(i);
                status.remove(i);
            }
        }
        System.out.println("list = " + list);
        // 빈 값은 checked 되어있어도 사용하지 않음
        list.removeIf(color -> color.trim().isEmpty());

        return list;
    }

    @Test
    void checkTest(){
        //given
        List<String> colors = new ArrayList<>();
        ArrayList<Boolean> ststus = new ArrayList<>();
        colors.add("red");  //ststus.add(true);
        colors.add("black");  //ststus.add(true);
        colors.add("green");  //ststus.add(false);
        colors.add("blue");  //ststus.add(true);
        colors.add("");  //ststus.add(true);
        ststus.add(true);
        ststus.add(true);
        ststus.add(false);
        ststus.add(true);
        ststus.add(true);
        //when
        List<String> res = this.colorAndSizeCheck(colors, ststus);

        //then  -> red, black, blue
        System.out.println("res = " + res);  //res = [red, black, blue]
    }
}