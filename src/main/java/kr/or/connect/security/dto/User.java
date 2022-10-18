package kr.or.connect.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String password;
    private String email;
    private String phone;
    private Date createDate;
    private Date modifyDate;

    public User() {
        createDate = new Date();
        modifyDate = new Date();
    }

    public User(Long id, String name, String password, String email,String phone) {
        this(); // 생성자 내부의 코드 블록 첫줄에 this()를 호출함으로 자신의 다른 생성자를 호출 할 수 있다.
                // 주의할 점은 생성자 코드블록 내부의 this() 위에 다른 소스코드가 존재해서는 안된다.
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }
}
