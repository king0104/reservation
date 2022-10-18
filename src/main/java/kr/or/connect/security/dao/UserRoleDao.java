package kr.or.connect.security.dao;

import kr.or.connect.security.dto.UserRole;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRoleDao {
    private static final String SELECT_ALL_BY_EMAIL =
            "SELECT ur.id, ur.user_id, ur.role_name " +
                    "FROM user_role ur " +
                    "JOIN user u ON ur.user_id = u.id " +
                    "WHERE u.email = :email";

    private static final String INSERT_ADMIN_ROLE =
            "INSERT INTO user_role(user_id, role_name) " +
                    "VALUES (:userId, \"ROLE_ADMIN\")";

    private static final String INSERT_USER_ROLE =
            "INSERT INTO user_role(user_id, role_name) " +
                    "VALUES (:userId, \"ROLE_USER\")";

    private NamedParameterJdbcTemplate jdbc;
    // BeanPropertyRowMapper는 Role클래스의 프로퍼티를 보고 자동으로 칼럼과 맵핑해주는 RowMapper객체를 생성한다.
    // roleId 프로퍼티는 role_id 칼럼과 맵핑이 된다.
    // 만약 프로퍼티와 칼럼의 규칙이 맞아 떨어지지 않는다면 직접 RowMapper객체를 생성해야 한다.
    // 생성하는 방법은 아래의 rowMapper2를 참고한다.
    private RowMapper<UserRole> rowMapper = BeanPropertyRowMapper.newInstance(UserRole.class);

    public UserRoleDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<UserRole> getRolesByEmail(String email){
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        return jdbc.query(SELECT_ALL_BY_EMAIL, params, rowMapper);
    }

    public void addAdminRole(Long userId) {
        Map<String, Object> params = Collections.singletonMap("userId", userId);
        jdbc.update(INSERT_ADMIN_ROLE,params);
    }

    public void addUserRole(Long userId) {
        Map<String, Object> params = Collections.singletonMap("userId", userId);
        jdbc.update(INSERT_USER_ROLE, params);
    }


}