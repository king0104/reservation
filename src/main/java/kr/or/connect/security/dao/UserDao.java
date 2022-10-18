package kr.or.connect.security.dao;

import kr.or.connect.security.dto.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDao {
    private static final String SELECT_ALL_BY_EMAIL =
            "SELECT id, name, password, email, phone, create_date, modify_date " +
                    "FROM user " +
                    "WHERE email = :email";

    private static final String INSERT_USER =
            "INSERT INTO user(name, password, email, phone, create_date, modify_date) "
                    + "VALUES (:name, :password, :email, :phone, :createDate, :modifyDate);";

    private NamedParameterJdbcTemplate jdbc;
    private RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);

    public UserDao(DataSource dataSource){
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public User getUserByEmail(String email){
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        return jdbc.queryForObject(SELECT_ALL_BY_EMAIL, params, rowMapper);
    }

    public void addUser(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", user.getName());
        params.put("password", user.getPassword());
        params.put("email", user.getEmail());
        params.put("phone", user.getPhone());
        params.put("createDate", user.getCreateDate());
        params.put("modifyDate", user.getModifyDate());

        // Insert Query를 위해서 update method를 사용했다.
        jdbc.update(INSERT_USER, params);
    }
}
