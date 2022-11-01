package kr.or.connect.reservationinfo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
public class ReservationInfoAndProduct {
    private Long id;
    private Long productId;
    private Long displayInfoId;
    private int cancelFlag;

    private String productDescription;
    private String productContent;

    private Long userId;
    private int sumPrice;

    private Date reservationDate;
    private Date createDate;
    private Date modifyDate;

}
