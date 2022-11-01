package kr.or.connect.fileinfo.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.or.connect.comment.dto.Comment;
import kr.or.connect.fileinfo.dto.FileInfo;
import kr.or.connect.fileinfo.dto.FileInfoRegister;
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
import java.util.Map;

@Repository
public class FileInfoDao {

    private static final String SELECT =
            "SELECT id AS file_id, file_name, save_file_name, content_type, delete_flag, create_date, modify_date\n" +
                    "FROM file_info\n" +
                    "WHERE id = :id";

    private static final String INSERT_FILE_INFO =
            "INSERT INTO file_info(file_name, save_file_name, content_type,delete_flag, create_date, modify_date)\n" +
                    "VALUES (:fileName, :saveFileName, :contentType, :deleteFlag, NOW(), NOW())";


    private NamedParameterJdbcTemplate jdbcTemplate;
    private RowMapper<FileInfo> rowMapper = BeanPropertyRowMapper.newInstance(FileInfo.class);
    public FileInfoDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    public FileInfo select(int id) {
        return jdbcTemplate.queryForObject(SELECT, Collections.singletonMap("id", id), rowMapper);
    }

    public int insert(FileInfoRegister fileInfoRegister) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> fileMap = objectMapper.convertValue(fileInfoRegister, Map.class);

        SqlParameterSource fileParams = new MapSqlParameterSource()
                .addValues(fileMap);

        KeyHolder fileKeyHolder = new GeneratedKeyHolder();
        return jdbcTemplate.update(INSERT_FILE_INFO, fileParams, fileKeyHolder, new String[]{"id"});

    }

}
