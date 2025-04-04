package org.example.shoppingmall.repository.shipping;


import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.shipping.ShippingDto;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ShippingListRepository {

    //배송 정보 전체 조회
    List<ShippingDto> selectAllShipping();

    //배송 상세 페이지
    ShippingDto findDetailShippingId(String id);

    //배송 상세 페이지 수정 페이지
    ShippingDto updateShipping(String id);

    //배송 상세 페이지 수정 후 업데이트
    void updateShippingList(ShippingDto shippingDto);

    //고객 배송 조회
      ShippingDto getShippingTrack(String orderId);
}




