package kr.or.connect.comment.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ReservationUserCommentImage {
    private Long id;
    private Long reservationInfoId;
    private Long reservationUserCommentId;
    private Long fileId;

    @Builder
    public ReservationUserCommentImage(Long id, Long reservationInfoId, Long reservationUserCommentId, Long fileId) {
        this.id = id;
        this.reservationInfoId = reservationInfoId;
        this.reservationUserCommentId = reservationUserCommentId;
        this.fileId = fileId;
    }

}
