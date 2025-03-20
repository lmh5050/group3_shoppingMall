package org.example.shoppingmall.repository.shipping;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.product.ProductDto;
import org.example.shoppingmall.dto.shipping.ShippingDto;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ManagementRepository {

        ArrayList<ProductDto> getProductData();

        void setNewProduct(ProductDto productDto);

        ProductDto getProductById(String productId);

    }
