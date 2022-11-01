package kr.or.connect.reservationinfo.service;

import kr.or.connect.reservationinfo.dao.ReservationInfoDao;
import kr.or.connect.reservationinfo.dto.*;
import kr.or.connect.user.dao.UserTableDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationInfoService {
    private final ReservationInfoDao reservationInfoDao;
//    private final UserTableDao userTableDao; // - user 도메인에 security user 부분 옮기기

    /**
     * 스트림으로 처리하는 방법
     * pricelist -> ripinfoprice
     * id list 별로
     */
    @Transactional
    public ReservationInfoSaveResponse register(ReservationInfo reservationInfo, List<PriceRequest> priceRequestList) {

        // 리턴해줄 응답 dto
        ReservationInfoSaveResponse response = new ReservationInfoSaveResponse();

        // 1. add reservation info
        int reservationInfoId = reservationInfoDao.insertReservationInfo(reservationInfo);
        // 2. select reservation info
        // -- info 무의미(생각하고 쓰자)
        // -- selected -> new로 바꾸기
        ReservationInfo selectedReservationInfo = reservationInfoDao.selectReservationInfo(reservationInfoId);

        // _1. response에 reservation info정보 넣기
        // -- setter보다는 빌더패턴 사용하자
        response.setId(selectedReservationInfo.getId());
        response.setProductId(selectedReservationInfo.getProductId());
        response.setCancelFlag(selectedReservationInfo.getCancelFlag());
        response.setDisplayInfoId(selectedReservationInfo.getDisplayInfoId());
        response.setUserId(selectedReservationInfo.getUserId());
        response.setReservationDate(selectedReservationInfo.getReservationDate());
        response.setCreateDate(selectedReservationInfo.getCreateDate());
        response.setModifyDate(selectedReservationInfo.getModifyDate());

        // -- for문 전체를 함수로 따로 빼는 것이 좋음
        for (PriceRequest price : priceRequestList) {
            // 1. rip 하나 만들기
            ReservationInfoPrice reservationInfoPrice = ReservationInfoPrice.builder()
                    .reservationInfoId(selectedReservationInfo.getId())
                    .productPriceId(price.getProductPriceId())
                    .count(price.getCount())
                    .build();

            // 2. insert rip
            int reservationInfoPriceId = reservationInfoDao.insertReservationInfoPrice(reservationInfoPrice);
            // 3. select rip
            // _2. response에 reservation info price 넣기
            // (질문) response dto 생성 시 prices 필드를 new ArrayList()로 초기화해두어야 하나씩 add가 가능한데, 이게 맞는지 전체 한번에 할당하는게 맞는지
            response.getPrices().add(reservationInfoDao.selectReservationInfoPrice(reservationInfoPriceId));

        }

        return response;
    }
    // -- 클래스명에 And 안들어감
    // -- response, request 는 controller에서만 허용되는 것.(서비스까지는 허용될수도, 하지만 dao에서는 아예 안쓰임)

    // -- 포사드 패턴 (데코레이터 패턴보다 나은듯)
        // -- 컨트롤러와 서비스 사이에 있는 layer를 하나 더 만든다
        // -- 서비스에다가 response, request 던질 때도 바꿔서 던짐. 이름만 바꾸더라고 그렇다
        // -- 같은 layer 참조 안하고, 하위 layer만 참조하는 것이 원칙
    // -- 상호참조를 막으려고 생성자주입
    @Transactional
    public List<ReservationInfoAndProduct> get(int userId) {

        return reservationInfoDao.selectAllReservationInfoReadResponse(userId); // -- selectAll 로 바꾸기

    }
    // -- reservation id로 하는게 가독성 좋아보임
    @Transactional
    public int updateCancelFlag(int id) {
        return reservationInfoDao.updateCancelFlag(id);
    }

    @Transactional
    public int getProductId(int id) {
        return reservationInfoDao.selectProductId(id);
    }

}
