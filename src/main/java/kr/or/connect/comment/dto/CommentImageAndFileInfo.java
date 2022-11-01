package kr.or.connect.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentImageAndFileInfo {

    private int id;
    private int reservationInfoId;
    private int reservationUserCommentId;

    private int fileId;
    private String fileName;
    private String saveFileName;
    private String contentType;
    private int deleteFlag;
    private Date createDate;
    private Date modifyDate;

    @Builder
    public CommentImageAndFileInfo(int id, int reservationInfoId, int reservationUserCommentId, int fileId, String fileName, String saveFileName, String contentType, int deleteFlag, Date createDate, Date modifyDate) {
        this.id = id;
        this.reservationInfoId = reservationInfoId;
        this.reservationUserCommentId = reservationUserCommentId;
        this.fileId = fileId;
        this.fileName = fileName;
        this.saveFileName = saveFileName;
        this.contentType = contentType;
        this.deleteFlag = deleteFlag;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }
}
