package org.example.shoppingmall.repository.shipping;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.shipping.ShippingDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
public interface ShippingRepository {
   ArrayList<ShippingDto> shippingCompany();

}
