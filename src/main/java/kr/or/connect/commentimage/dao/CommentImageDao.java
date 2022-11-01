package kr.or.connect.commentimage.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.or.connect.commentimage.dto.CommentImage;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentImageDao {

    private static final String SELECT_ALL =
            "SELECT id, reservation_info_id, reservation_user_comment_id, file_id\n" +
                    "FROM reservation_user_comment_image\n" +
                    "WHERE reservation_user_comment_id = :reservationUserCommentId";

    private static final String INSERT =
            "INSERT INTO reservation_user_comment_image(reservation_info_id,reservation_user_comment_id, file_id)\n" +
                    "VALUES (:reservationInfoId, :reservationUserCommentId, :fileId)";

    private NamedParameterJdbcTemplate jdbc;
    public CommentImageDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }
    private RowMapper<CommentImage> commentImageRowMapper = BeanPropertyRowMapper.newInstance(CommentImage.class);



    public List<CommentImage> selectAll(int commentId) {
        return jdbc.query(SELECT_ALL, Collections.singletonMap("reservationUserCommentId", commentId), commentImageRowMapper);
    }


    public int insert(CommentImage commentImage) {
        // insert into reservation user comment image
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> commentImageMap = new HashMap<>();
        commentImageMap.put("reservationInfoId", commentImage.getReservationInfoId());
        commentImageMap.put("reservationUserCommentId", commentImage.getReservationUserCommentId());
        commentImageMap.put("fileId", commentImage.getFileId());

        SqlParameterSource commentImageParams = new MapSqlParameterSource()
                .addValues(commentImageMap);


        KeyHolder commentImageKeyHolder = new GeneratedKeyHolder();
        return jdbc.update(INSERT,commentImageParams,commentImageKeyHolder,new String[]{"id"});

    }


}
