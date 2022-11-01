package kr.or.connect.reservationinfo.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ReservationInfoAndProductResponse {

    private int size;
    private List<ReservationInfoAndProduct> items;

}
