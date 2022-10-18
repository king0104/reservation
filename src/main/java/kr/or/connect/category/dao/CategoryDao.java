package kr.or.connect.category.dao;

import kr.or.connect.category.dto.Category;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@Repository
public class CategoryDao {
    private static final String SELECT_ALL =
            // join문 작성하기
            // rowMapper 사용하므로, 컬럼명 맞춰줘야 한다(as count 쓰기)
            // 예약어는 대문자로, table alias는 as안붙임,group by에 들어간 테이블 칼럼에 대해서는 묶기(max같은걸로)
            "SELECT c.id, c.name, count(di.id) AS count\n" +
                    "FROM category c\n" +
                    "INNER JOIN product p ON c.id = p.category_id\n" +
                    "INNER JOIN display_info di ON p.id = di.product_id\n" +
                    "GROUP BY c.id";

    // 빈으로 만들어서 주입 가능하게 할 수 있었을듯
    private NamedParameterJdbcTemplate jdbc;
    private RowMapper<Category> rowMapper = BeanPropertyRowMapper.newInstance(Category.class);

    public CategoryDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Category> selectAll() {
        return jdbc.query(SELECT_ALL, Collections.emptyMap(), rowMapper);
    }



}
