package kr.or.connect.promotion.dao;

import kr.or.connect.promotion.dto.Promotion;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@Repository
public class PromotionDao {
    // inner join 명시적으로 적으면 좋을듯
    private static final String SELECT_All =
            "SELECT pm.id, p.id product_id, c.id category_id, c.name, p.description, pi.file_id\n" +
            "FROM promotion pm\n" +
            "INNER JOIN product p ON pm.product_id = p.id\n" +
            "INNER JOIN category c ON p.category_id = c.id\n" +
            "INNER JOIN product_image pi ON p.id = pi.product_id\n" +
            "WHERE pi.type = 'ma'";

    private NamedParameterJdbcTemplate jdbc;
    private RowMapper<Promotion> rowMapper = BeanPropertyRowMapper.newInstance(Promotion.class);

    public PromotionDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Promotion> selectAll() {
        return jdbc.query(SELECT_All, Collections.emptyMap(), rowMapper);
    }

}
