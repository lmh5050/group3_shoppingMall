package org.example.shoppingmall.service.Shipping;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.dto.shipping.ShippingDto;
import org.example.shoppingmall.repository.shipping.ManagementRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingService {
    private final ManagementRepository managementRepository;

    public void shippingCompany(ShippingDto shippingDto) {
        managementRepository.shippingCompany(shippingDto);
    }
}
