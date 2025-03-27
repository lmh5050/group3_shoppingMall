package org.example.shoppingmall.repository.shipping;


import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.shipping.ShippingDto;

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

    //배송 리스트 삭제
    void deleteShippingList(String id);


    //  void test(ShippingDto shippingDto);
//
//    List<ShippingDto> findAll2();
//
//    ShippingDto findById(Long id);
//
//    void update(ShippingDto shippingDto);
//
//    void delete(Long id);
}


