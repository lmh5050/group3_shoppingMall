package org.example.shoppingmall.service.Shipping;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.dto.shipping.ShippingDto;
import org.example.shoppingmall.repository.shipping.ShippingListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Transactional
    public void updateShippingListId(ShippingDto shippingDto) {
        shippingListRepository.updateShippingList(shippingDto);
        // 배송 아이디를 통해 업데이트 된 로우를 가져와서 이력 테이블에 그대로 저장함
        ShippingDto getShippingDto = shippingListRepository.getShippingDtoByPk(shippingDto.getShippingId() );
        this.insertShippingHistory(getShippingDto);
    }
    //배송 이력 삭제
    public void deleteShipping(String id){
        shippingListRepository.deleteShippingList(id);
    }
    //배송 이력 리스트 페이지
    public ArrayList<ShippingDto> getShippingDetailList(String id){
        return shippingListRepository.getShippingDetail(id);
    }
    //배송 이력
    public void insertShippingHistory(ShippingDto shippingDto) {
        shippingListRepository.insertShippingHistory(shippingDto);
    }
    //고객 배송 조회
    public ArrayList<ShippingDto> getShippingListTrack(String orderId){
        return shippingListRepository.getShippingTrack(orderId);
    }
//    //고객 배송 조회
//    public ShippingDto getLatestShippingDetail(String orderId) {
//        // 최신 배송 정보만 가져오는 로직 (shipping_datetime 기준으로 최신 1개만)
//        return shippingListRepository.getLatestShippingDetail(orderId);


}


