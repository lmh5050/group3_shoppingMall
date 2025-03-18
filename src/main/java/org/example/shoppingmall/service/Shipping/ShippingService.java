package org.example.shoppingmall.service.Shipping;

import org.example.shoppingmall.dto.shipping.ShippingDto;
import org.example.shoppingmall.repository.shipping.ShippingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ShippingService {
    private final ShippingRepository shippingRepository;

    public ShippingService(ShippingRepository shipp) {
        this.shippingRepository = shipp;
    }

   public ArrayList<ShippingDto> shippingCompany(){
        return shippingRepository.shippingCompany();
    }
}
