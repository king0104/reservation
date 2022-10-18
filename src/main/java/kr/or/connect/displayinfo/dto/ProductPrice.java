package kr.or.connect.displayinfo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ProductPrice {
    // product_price
    private Long id;
    private Long productId;
    private String priceTypeName;
    private String price;
    private String discountRate;
    private Date createDate;
    private Date modifyDate;
}
