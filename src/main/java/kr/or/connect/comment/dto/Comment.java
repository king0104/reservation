package kr.or.connect.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Comment {
    // reservation_user_comment
    private int id;
    private int productId;
    private int reservationInfoId;
    private int score;
    private int userId;
    private String comment;
    private Date createDate;
    private Date modifyDate;
}
