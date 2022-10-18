package kr.or.connect.reservationinfo.service;

import io.swagger.models.auth.In;
import kr.or.connect.reservationinfo.dao.ReservationInfoDao;
import kr.or.connect.reservationinfo.dto.PriceRequest;
import kr.or.connect.reservationinfo.dto.ReservationInfo;
import kr.or.connect.reservationinfo.dto.ReservationInfoPrice;
import kr.or.connect.reservationinfo.dto.ReservationInfoSaveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationInfoService {
    private final ReservationInfoDao reservationInfoDao;

    @Transactional
    public ReservationInfoSaveResponse register(ReservationInfo reservationInfo, List<PriceRequest> priceRequestList) {

        // 리턴해줄 응답 dto
        ReservationInfoSaveResponse response = new ReservationInfoSaveResponse();

        // 1. add reservation info
        int reservationInfoId = reservationInfoDao.insertReservationInfo(reservationInfo);
        // 2. select reservation info
        ReservationInfo selectedReservationInfo = reservationInfoDao.selectReservationInfo(reservationInfoId);

        // _1. response에 reservation info정보 넣기
        response.setId(selectedReservationInfo.getId());
        response.setProductId(selectedReservationInfo.getProductId());
        response.setCancelFlag(selectedReservationInfo.getCancelFlag());
        response.setDisplayInfoId(selectedReservationInfo.getDisplayInfoId());
        response.setUserId(selectedReservationInfo.getUserId());
        response.setReservationDate(selectedReservationInfo.getReservationDate());
        response.setCreateDate(selectedReservationInfo.getCreateDate());
        response.setModifyDate(selectedReservationInfo.getModifyDate());

        // price를 하나씩 꺼내서, rip를 만든 후, rip로 insertRIP를 실행해야 한다,

        //[질문]
        // 뭔가 stream을 억지로 쓴 느낌인데...
        // 이게 억지로 쓴게 맞는지?
        // 억지라면, 이럻게라도 쓰는게 좋은지? or for each문 사용할지?
        priceRequestList.stream()
                .map(priceRequest -> ReservationInfoPrice.builder()
                        .productPriceId(priceRequest.getProductPriceId())
                        .reservationInfoId(selectedReservationInfo.getId())
                        .count(priceRequest.getCount())
                        .build())
                // 3. insert reservation info price
                .map(reservationInfoPrice ->
                        reservationInfoDao.insertReservationInfoPrice(reservationInfoPrice))
                // 4. select reservation info price
                .map(reservationInfoPriceId ->
                        reservationInfoDao.selectReservationInfoPrice(reservationInfoPriceId))
                // _2. response에 reservation info price 정보 넣기
                .forEach(selectedReservationInfoPrice -> response.getPrices().add(selectedReservationInfoPrice));


        return response;

    }

}
