package org.example.shoppingmall.repository.shipping;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.shipping.ShippingCompanyDto;

import java.util.ArrayList;

@Mapper
public interface ShippingRepository {
   ArrayList<ShippingCompanyDto> shippingCompany();

}
