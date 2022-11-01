package kr.or.connect.comment.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.or.connect.commentimage.dto.CommentImage;
import kr.or.connect.comment.dto.CommentRegister;
import kr.or.connect.comment.dto.Comment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentDao {
    /**
     * 한방쿼리로 dto를 만들 수 있나..?
     * 1.일단 해보자 -> 안됨
     *
     * 2.쿼리 2개로 만들어서, new Comment를 하자
     *
     * 3.한방쿼리로 값을 받은 후, comment를 만드는데, mapper 동작 전, rui를 만들고 mapper 만들기
     *  -> 이걸 dao에서 하는건지.. serivce에서 하는건지..?
     *  -> service에서 하면 새로운 dto를 만들어야 하니까 일단 dao에서 해보자 (x)
     *  -> 새로운 dto 만들고, service에서 하자 (o)
     *
     * 4.
     */
    private static final String SELECT_TOTAL_COUNT =
            "SELECT COUNT(*)\n" + // -- COUNT(1), COUNT(*) : 해당 로우 있으면 센다 // -- COUNT(column) : 해당 컬럼이 null이 아닌 것만 가져옴
                    "FROM user AS u\n" +
                    "INNER JOIN reservation_user_comment ruc on u.id = ruc.user_id\n" +
                    "WHERE ruc.product_id = :productId\n";

    // 1. product 값 없으면 ..? -> null 들어옴
    // 2. start 값 없으면 -> 0 들어옴
    // productId == 0이면 where 절을 안걸고 싶고,
    // 0이 아니면 where절을 활성화시키고 싶다
    private static final String SELECT_All =
            "SELECT id, product_id, reservation_info_id, score, user_id, comment, create_date, modify_date\n" +
                    "FROM reservation_user_comment \n" +
                    "WHERE product_id = :productId\n" +
                    "ORDER BY id DESC\n" +
                    "LIMIT 5 OFFSET :start "; // -- limit 5 상수 때려박는건 비즈니스 로직에서 받아와야함



    private static final String INSERT_RESERVATION_USER_COMMENT =
            "INSERT INTO reservation_user_comment(product_id, reservation_info_id, user_id, score, comment, create_date, modify_date)\n" +
                    "VALUES (:productId,:reservationInfoId,:userId,:score,:comment,now(),now())";


    private NamedParameterJdbcTemplate jdbc;
    private RowMapper<Comment> CommentRowMapper = BeanPropertyRowMapper.newInstance(Comment.class);
    private RowMapper<CommentImage> reservationUserCommentImageRowMapper = BeanPropertyRowMapper.newInstance(CommentImage.class);

    public CommentDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public int selectTotalCount(int productId) {
        Map<String,Object> params = new HashMap<>();
        params.put("productId", productId);

        return jdbc.queryForObject(SELECT_TOTAL_COUNT, params, Integer.class);
    }

    // -- constant string
    // -- java8 버전과 그 이전 버전 차이
        // -- jvm 메모리 구조가 여기서 바뀜
        // -- java8 버전 메모리 구조 이해해보기

    // -- 패키지에서라도 info를 빼야 한다
    public List<Comment> selectAll(Integer productId, Integer start) {
        Map<String,Object> params = new HashMap<>();
        params.put("productId", productId); // -- 상수로 뺀다
        params.put("start", start);

        return jdbc.query(SELECT_All, params, CommentRowMapper);
    }


    public int insert(CommentRegister reservationUserComment) {
        // insert into reservation user comment
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> commentMap = objectMapper.convertValue(reservationUserComment, Map.class);

        SqlParameterSource commentParams = new MapSqlParameterSource()
                .addValues(commentMap);

        System.out.println(commentParams);

        KeyHolder commentKeyHolder = new GeneratedKeyHolder();
        return jdbc.update(INSERT_RESERVATION_USER_COMMENT, commentParams, commentKeyHolder, new String[]{"id"});
    }

}
