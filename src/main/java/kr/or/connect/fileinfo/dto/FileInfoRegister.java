package kr.or.connect.fileinfo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FileInfoRegister {
    private String fileName;
    private String saveFileName;
    private String contentType;
    private int deleteFlag;
    private Date createDate;
    private Date modifyDate;

    @Builder
    public FileInfoRegister(String fileName, String saveFileName, String contentType, int deleteFlag) {
        this.fileName = fileName;
        this.saveFileName = saveFileName;
        this.contentType = contentType;
        this.deleteFlag = deleteFlag;
    }


}
