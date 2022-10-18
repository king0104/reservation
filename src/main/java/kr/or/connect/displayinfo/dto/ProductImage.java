package kr.or.connect.displayinfo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ProductImage {
    // product_image
    private Long productId;
    private Long productImageId;
    private String type;
    // file_info
    private String fileInfoId;
    private String fileName;
    private String saveFileName;
    private String contentType;
    private String deleteFlag;
    private Date createDate;
    private Date modifyDate;
}
