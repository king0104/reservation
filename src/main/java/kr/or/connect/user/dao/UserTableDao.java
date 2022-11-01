package kr.or.connect.user.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;

@Repository
public class UserTableDao {

    private static final String SELECT_USER_ID =
            "SELECT id\n" +
                    "FROM user\n" +
                    "WHERE email = :username";

    private NamedParameterJdbcTemplate jdbc;

    public UserTableDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public Integer selectUserId(String username) {
        return jdbc.queryForObject(SELECT_USER_ID, Collections.singletonMap("username",username),Integer.class);
    }

}
