package kr.or.connect.category.dto;

import lombok.*;

@Getter
@Setter // rowMapper 때문에 반드시 필요
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category {
    private Long id;
    private String name;
    private int count;

}
