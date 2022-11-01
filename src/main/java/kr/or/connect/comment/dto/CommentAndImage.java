package kr.or.connect.comment.dto;

import kr.or.connect.commentimage.dto.CommentImage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommentAndImage {
    // reservation_user_comment
    private int id;
    private int productId;
    private int reservationInfoId;
    private int score;
    private int userId;
    private String comment;

    // reservation_user_comment_image
    // 하나의 테이블 자체를 컬럼으로 가지고 있어도 되는지 - 가능
    private List<CommentImageAndFileInfo> reservationUserCommentImages;

    @Builder
    public CommentAndImage(int id, int productId, int reservationInfoId, int score, int userId, String comment, List<CommentImageAndFileInfo> reservationUserCommentImages) {
        this.id = id;
        this.productId = productId;
        this.reservationInfoId = reservationInfoId;
        this.score = score;
        this.userId = userId;
        this.comment = comment;
        this.reservationUserCommentImages = reservationUserCommentImages;
    }
}
