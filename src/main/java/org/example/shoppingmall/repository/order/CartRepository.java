package org.example.shoppingmall.repository.order;
import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.order.CartDto;
import java.util.List;

@Mapper
public interface CartRepository {
    List<CartDto> getCartList (String customerId);
    void updateCartData (List<CartDto> cartData);
    void insertCartData (CartDto cartData);
}
