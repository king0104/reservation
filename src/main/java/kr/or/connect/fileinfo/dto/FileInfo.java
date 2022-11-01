package kr.or.connect.fileinfo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class FileInfo {

    private int fileId;
    private String fileName;
    private String saveFileName;
    private String contentType;
    private int deleteFlag;
    private Date createDate;
    private Date modifyDate;

    @Builder
    public FileInfo(int fileId, String fileName, String saveFileName, String contentType, int deleteFlag, Date createDate, Date modifyDate) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.saveFileName = saveFileName;
        this.contentType = contentType;
        this.deleteFlag = deleteFlag;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }




}
