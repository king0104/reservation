package kr.or.connect.reservationinfo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

// - vaildation check를 할 수 있다
// - post는 자주 함
@Getter
@Setter
public class ReservationInfoSaveRequest {
    private List<PriceRequest> prices;
    private Long productId;
    private Long displayInfoId;
    private String reservationYearMonthDay; // - Date
    private Long userId;
}