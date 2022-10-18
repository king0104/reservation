package kr.or.connect.reservationinfo.dto;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class ReservationInfoPrice {
    private Long id;
    private Long reservationInfoId;
    private Long productPriceId;
    private int count;

    @Builder
    public ReservationInfoPrice(Long reservationInfoId, Long productPriceId, int count) {
        this.reservationInfoId = reservationInfoId;
        this.productPriceId = productPriceId;
        this.count = count;
    }
}
