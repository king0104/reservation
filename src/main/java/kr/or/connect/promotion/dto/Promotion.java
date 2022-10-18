package kr.or.connect.promotion.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Promotion {
    private Long id;
    private Long productId;
    private Long categoryId;
    private String categoryName;
    private String description;
    private int fileId;
}
