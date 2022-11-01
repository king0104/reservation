package kr.or.connect.commentimage.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CommentImage {
    private int id;
    private int reservationInfoId;
    private int reservationUserCommentId;
    private int fileId;

    @Builder
    public CommentImage(int id, int reservationInfoId, int reservationUserCommentId, int fileId) {
        this.id = id;
        this.reservationInfoId = reservationInfoId;
        this.reservationUserCommentId = reservationUserCommentId;
        this.fileId = fileId;
    }

    @Builder
    public CommentImage(int reservationInfoId, int reservationUserCommentId, int fileId) {
        this.reservationInfoId = reservationInfoId;
        this.reservationUserCommentId = reservationUserCommentId;
        this.fileId = fileId;
    }

}
