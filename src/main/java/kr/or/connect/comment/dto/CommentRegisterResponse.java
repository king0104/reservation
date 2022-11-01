package kr.or.connect.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentRegisterResponse {
    private String result;
    private int productId;

}
