package org.example.shoppingmall.service.product;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductManagementServiceTest {

    @Test
    void trimTest(){
        // given
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");

        System.out.println("list = " + list);
        // when
        list.removeIf(color -> color.trim().isEmpty());
        // then
        System.out.println("list = " + list);
    }
}