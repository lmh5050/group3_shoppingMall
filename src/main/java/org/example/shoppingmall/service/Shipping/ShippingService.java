package org.example.shoppingmall.service.Shipping;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.dto.shipping.ShippingDto;
import org.example.shoppingmall.repository.shipping.ShippingListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingService {
    private final ShippingListRepository shippingListRepository;

    //배송리스트 전체 조회
    public List<ShippingDto> getAllShipping(){
        return shippingListRepository.selectAllShipping();
    }
    //배송 상세 페이지
    public ShippingDto getDetailShipping(String id) {
        return shippingListRepository.findDetailShippingId(id);
    }
    //배송 상세 페이지 수정 페이지
    public ShippingDto updateShippingId(String id) {
        return shippingListRepository.updateShipping(id);
    }
    //배송 상세 페이지 수정 후 업데이트
    public void updateShippingListId(ShippingDto shippingDto) {
        shippingListRepository.updateShippingList(shippingDto);
    }
    public void deleteShipping(String id){
        shippingListRepository.deleteShippingList(id);
    }
    //    public List<ShippingDto> findAll() {
//        return managementRepository.findAll2();
//    }

//
//    public ShippingDto findById(Long id) {
//        return managementRepository.findById(id);
//    }
//
//    public void update(ShippingDto shippingDto) {
//        managementRepository.update(shippingDto);
//    }
//
//    public void delete(Long id) {
//        managementRepository.delete(id);
//    }
}

