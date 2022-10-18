package kr.or.connect.comment.dao;

import kr.or.connect.comment.dto.Comment;
import kr.or.connect.comment.dto.ReservationUserCommentImage;
import kr.or.connect.comment.dto.SemiComment;
import kr.or.connect.promotion.dto.Promotion;
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
    private static final String SELECT_ALL_SEMI_COMMENT_COUNT =
            "SELECT COUNT(*)\n" +
                    "FROM user AS u\n" +
                    "INNER JOIN reservation_user_comment ruc on u.id = ruc.user_id\n" +
                    "WHERE ruc.product_id = :productId\n";

    // 1. product 값 없으면 ..? -> null 들어옴
    // 2. start 값 없으면 -> 0 들어옴
    // productId == 0이면 where 절을 안걸고 싶고,
    // 0이 아니면 where절을 활성화시키고 싶다
    private static final String SELECT_All_SEMI_COMMENT =
            "SELECT ruc.id AS id, ruc.product_id, ruc.reservation_info_id AS reservation_info_id, ruc.score, u.email AS reservation_email, ruc.create_date, ruc.modify_date\n" +
                    "FROM user AS u\n" +
                    "INNER JOIN reservation_user_comment ruc on u.id = ruc.user_id\n" +
                    "WHERE ruc.product_id = :productId\n" +
                    "ORDER BY ruc.id DESC\n" +
                    "LIMIT 5 OFFSET :start ";

    private static final String SELECT_ALL_RESERVATION_USER_COMMENT_IMAGE =
            "SELECT ruci.id, ruci.reservation_info_id, ruci.reservation_user_comment_id, ruci.file_id\n" +
                    "FROM reservation_user_comment ruc\n" +
                    "LEFT JOIN reservation_user_comment_image ruci on ruc.id = ruci.reservation_user_comment_id\n" +
                    "WHERE ruc.product_id = :productId\n" +
                    "ORDER BY ruc.id DESC\n" +
                    "LIMIT 5 OFFSET :start";

    private NamedParameterJdbcTemplate jdbc;
    private RowMapper<SemiComment> semiCommentRowMapper = BeanPropertyRowMapper.newInstance(SemiComment.class);
    private RowMapper<ReservationUserCommentImage> reservationUserCommentImageRowMapper = BeanPropertyRowMapper.newInstance(ReservationUserCommentImage.class);

    public CommentDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    public Integer selectAllSemiCommentCount(Integer productId) {
        Map<String,Object> params = new HashMap<>();
        params.put("productId", productId);

        return jdbc.queryForObject(SELECT_ALL_SEMI_COMMENT_COUNT, params, Integer.class);
    }

    public List<SemiComment> selectAllSemiComment(Integer productId, Integer start) {
        Map<String,Object> params = new HashMap<>();
        params.put("productId", productId);
        params.put("start", start);

        return jdbc.query(SELECT_All_SEMI_COMMENT, params, semiCommentRowMapper);
    }

    public List<ReservationUserCommentImage> selectAllReservationUserCommentImage(Integer productId, Integer start) {
        Map<String,Object> params = new HashMap<>();
        params.put("productId", productId);
        params.put("start", start);

        return jdbc.query(SELECT_ALL_RESERVATION_USER_COMMENT_IMAGE, params, reservationUserCommentImageRowMapper);
    }

}
