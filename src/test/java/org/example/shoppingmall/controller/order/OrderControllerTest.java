package org.example.shoppingmall.controller.order;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderControllerTest {

    @Test
    void orderListTest() {
        // given
        List<Long> test = new ArrayList<>();
        test.add(2L);
        test.add(3L);
        test.add(1L);
        test.add(4L);
        test.add(6L);
        test.add(5L);
        // when
        System.out.println("test = " + test);
        test.sort(Long::compareTo);
        System.out.println("test = " + test);
        //then
    }

}