package kr.or.connect.comment.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CommentRegisterRequest {
    private int reservationInfoId;
    private int score;
    private String comment;
    private MultipartFile multipartFile;
}
