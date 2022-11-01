package kr.or.connect.comment.dto;

import kr.or.connect.commentimage.dto.CommentImage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class CommentResponse {

    private int totalCount;
    private int commentCount;
    private List<CommentAndImage> commentAndImages;

    @Builder
    public CommentResponse(int totalCount, int commentCount, List<CommentAndImage> commentAndImages) {
        this.totalCount = totalCount;
        this.commentCount = commentCount;
        this.commentAndImages = commentAndImages;
    }
}
