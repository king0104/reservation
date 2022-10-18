package kr.or.connect.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRole {
    private Long id;
    private Long userId;
    private String roleName;

    public UserRole(Long userId, String roleName) {
        this.userId = userId;
        this.roleName = roleName;
    }
}
