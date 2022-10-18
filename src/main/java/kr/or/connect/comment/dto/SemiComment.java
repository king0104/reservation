package kr.or.connect.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SemiComment {
    // reservation_user_comment
    private Long id;
    private Long productId;
    private Long reservationInfoId;
    private int score;

    // user
    private String reservationEmail;

    // reservation_user_comment
    private Date createDate;
    private Date modifyDate;
}
