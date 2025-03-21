package org.example.shoppingmall.service.Shipping;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.dto.shipping.ShippingDto;
import org.example.shoppingmall.repository.shipping.ManagementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingService {
    private final ManagementRepository managementRepository;



    public void test(ShippingDto shippingDto) {
        managementRepository.test(shippingDto);

    }
    public List<ShippingDto> findAll() {
        return managementRepository.findAll2();
    }

    public ShippingDto findById(Long id) {;
        return managementRepository.findById(id);
    }
}
