package kr.or.connect.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class Comment {
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

    // reservation_user_comment_image
    // 하나의 테이블 자체를 컬럼으로 가지고 있어도 되는지
    private List<ReservationUserCommentImage> reservationUserCommentImages;

    @Builder
    public Comment(Long id, Long productId, Long reservationInfoId, int score, String reservationEmail, Date createDate, Date modifyDate, List<ReservationUserCommentImage> reservationUserCommentImages) {
        this.id = id;
        this.productId = productId;
        this.reservationInfoId = reservationInfoId;
        this.score = score;
        this.reservationEmail = reservationEmail;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.reservationUserCommentImages = reservationUserCommentImages;
    }
}
