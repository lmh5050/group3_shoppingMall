package org.example.shoppingmall.service.Shipping;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.dto.CodeDetailDto;
import org.example.shoppingmall.dto.shipping.ShippingDto;
import org.example.shoppingmall.repository.CodeDetailRepository;
import org.example.shoppingmall.repository.shipping.ShippingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingService {
    private final ShippingListRepository shippingListRepository;

    @Autowired
    private CodeDetailRepository codeDetailRepository;

    //status 값에 해당 code_Name 값 반환
    private String getCodeNameByStatus(String status) {
        CodeDetailDto codeDetailDto = codeDetailRepository.findCodeNameByStatus(status);
        return codeDetailDto.getCodeName();
    }

    //delayReason 값에 해당 code_Name 값 반환
    private String getCodeNameByDelayReason(String delayReason) {
        CodeDetailDto codeDetailDto = codeDetailRepository.findCodeNameByDelayReason(delayReason);
        return codeDetailDto.getCodeName();
    }

    //배송리스트 전체 조회
    public List<ShippingDto> getAllShipping() {
        return shippingListRepository.selectAllShipping();
    }

    //배송 상세 정보 조회 페이지
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
        //배송 완료일이 있으면 배송 완료
        if (shippingDto.getEndDatetime() != null) {
            shippingDto.setStatus("MA02006");
            //배송 일자가 있으면 배송 중
        } else if (shippingDto.getShippingDatetime() != null) {
            shippingDto.setStatus("MA02004");
            //배송 예정일자가 있으면 배송 준비 중
        } else if (shippingDto.getExpectedDatetime() != null) {
            shippingDto.setStatus("MA02002");
            //배송 예정일자 없이 기사 정보&운송장 번호만 있으면 상품 확인 중
        } else {
            shippingDto.setStatus("MA02001");
        }
        //배송 상태 코드에 대한 이름 변환 적용 (한 번만 실행)
        shippingDto.setStatus(getCodeNameByStatus(shippingDto.getStatus()));

        //지연 사유 상태 코드에 대한 이름 변환 적용 (한 번만 실행)
        shippingDto.setDelayReason(getCodeNameByDelayReason(shippingDto.getDelayReason()));

        //운송장 뒤에 , 찍힘을 공백으로 대체
        shippingDto.setTrackingNumber(shippingDto.getTrackingNumber().replace(",", ""));

        //수정된 배송 정보 저장
        shippingListRepository.updateShippingList(shippingDto);
    }

    //주문 아이디로 고객 배송 조회
    public ShippingDto getShippingListTrack(String orderId) {
        return shippingListRepository.getShippingTrack(orderId);

    }
}

