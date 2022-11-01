package kr.or.connect.reservationinfo.dto;

import lombok.*;

import java.util.Date;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class ReservationInfo {

    private Long id;
    private Long productId;
    private Long displayInfoId;
    private Long userId;
    private Date reservationDate;
    private int cancelFlag;
    private Date createDate;
    private Date modifyDate;

    // 안들어가는 부분은, null 처리된다(int는 자동으로 0이 들어간다 - 일관성 유지 위해 cancelFlag를 Long으로 바꿈)
    @Builder
    public ReservationInfo(Long productId, Long displayInfoId, Long userId, Date reservationDate, int cancelFlag) {
        this.productId = productId;
        this.displayInfoId = displayInfoId;
        this.userId = userId;
        this.reservationDate = reservationDate;
        this.cancelFlag = cancelFlag;
    }

    @Builder
    public ReservationInfo(Long id, Long productId, Long displayInfoId, Long userId, Date reservationDate, int cancelFlag, Date createDate, Date modifyDate) {
        this.id = id;
        this.productId = productId;
        this.displayInfoId = displayInfoId;
        this.userId = userId;
        this.reservationDate = reservationDate;
        this.cancelFlag = cancelFlag;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }

}
