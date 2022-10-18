package kr.or.connect.displayinfo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class DisplayInfoImage {
    // display_info_image
    private Long id;
    private Long displayInfoId;
    private Long fileId;
    // file_info
    private String fileName;
    private String saveFileName;
    private String contentType;
    private String deleteFlag;
    private Date createDate;
    private Date modifyDate;
}
