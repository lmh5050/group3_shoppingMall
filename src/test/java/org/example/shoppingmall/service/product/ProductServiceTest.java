package org.example.shoppingmall.service.product;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

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
}