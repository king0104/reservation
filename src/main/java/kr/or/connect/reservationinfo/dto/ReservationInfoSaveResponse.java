package kr.or.connect.reservationinfo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ToString
@Getter
@Setter
public class ReservationInfoSaveResponse {
    private Long id;
    private Long productId;
    private int cancelFlag;
    private Long displayInfoId;
    private Long userId;
    private Date reservationDate;
    private Date createDate;
    private Date modifyDate;
    // np ex때문에 이렇게 했는데, 다른 필드와 다르게 초기화하는게 일관성이 없어보여서요...
    // dto의 list는 원래 이렇게 초기화를 해주는게 일반적인가요?
    private List<ReservationInfoPrice> prices = new ArrayList<>();

}
