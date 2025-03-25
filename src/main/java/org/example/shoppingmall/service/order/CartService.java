package org.example.shoppingmall.service.order;

import org.example.shoppingmall.dto.order.CartDto;
import org.springframework.stereotype.Service;
import org.example.shoppingmall.repository.order.CartRepository;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<CartDto> getCartList(String customerId) {
        List<CartDto> cartList = cartRepository.getCartList(customerId);
        return cartList;
    }

    public void updateCartData(List<CartDto> cartData) {
        cartRepository.updateCartData(cartData);
    }

    public void insertCartData(CartDto cartData) {
        cartRepository.insertCartData(cartData);
    }




}
