package org.example.shoppingmall.service.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
}