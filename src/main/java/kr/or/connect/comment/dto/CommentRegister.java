package kr.or.connect.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentRegister {
    private int productId;
    private int reservationInfoId;
    private int userId;
    private int score;
    private String comment;

}
