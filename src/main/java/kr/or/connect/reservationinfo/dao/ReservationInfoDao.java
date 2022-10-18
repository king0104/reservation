package kr.or.connect.reservationinfo.dao;

import kr.or.connect.reservationinfo.dto.ReservationInfo;
import kr.or.connect.reservationinfo.dto.ReservationInfoPrice;
import kr.or.connect.reservationinfo.dto.ReservationInfoSaveRequest;
import kr.or.connect.reservationinfo.dto.ReservationInfoSaveResponse;
import kr.or.connect.security.dto.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ReservationInfoDao {

//    private static final String INSERT_RESERVATION_INFO =
//           "INSERT INTO reservation_info(product_id, display_info_id, user_id, reservation_date, cancel_flag, create_date, modify_date)\n" +
//                   "VALUES (:product_id, :display_info_id, :user_id, :reservation_date, 0, NOW(), NOW())";
//
//    private static final String INSERT_RESERVATION_INFO_PRICE =
//            "INSERT INTO reservation_info_price(reservation_info_id, product_price_id, count)\n" +
//                    "VALUES (:reservation_info_id, :product_price_id, :count)";

    private static final String SELECT_RESERVATION_INFO =
            "SELECT id, product_id, display_info_id, user_id, reservation_date, cancel_flag, create_date, modify_date\n" +
                    "FROM reservation_info\n" +
                    "WHERE id = :reservationInfoId";

    private static final String SELECT_RESERVATION_INFO_PRICE =
            "SELECT id, reservation_info_id, product_price_id, count\n" +
                    "FROM reservation_info_price\n" +
                    "WHERE id = :reservationInfoPriceId";

//    왜 이 코드는 안되는지
//    private final DataSource dataSource;
//    private NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);

    private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsertOperations insertActionWithReservationInfo;
    private SimpleJdbcInsertOperations insertActionWithReservationInfoPrice;
    private RowMapper<ReservationInfo> ReservationInfoMapper = BeanPropertyRowMapper.newInstance(ReservationInfo.class);
    private RowMapper<ReservationInfoPrice> ReservationInfoPriceMapper = BeanPropertyRowMapper.newInstance(ReservationInfoPrice.class);

    public ReservationInfoDao(DataSource dataSource) {
//        jdbc.update() // - 쿼리 유연함
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        // - 자바에서 Date를 넘겨주기 (사실 좋은 방법은 아님) or 테이블의 default값으로 처리하는
        // - 보통 mysql 시간으로 처리한다
        this.insertActionWithReservationInfo = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_info")
                .usingColumns("product_id","display_info_id","user_id","reservation_date","create_date","modify_date") // 이걸로 지정해서 insert문 보내보기
                .usingGeneratedKeyColumns("id");

        this.insertActionWithReservationInfoPrice = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_info_price")
                // 이거 바꾸기
                .usingColumns("reservation_info_id","product_price_id","count") // 이걸로 지정해서 insert문 보내보기
                .usingGeneratedKeyColumns("id");
    }
    // 1. reservation_info 만들어서 insert
    // 이때, pk값 알아와야한다
    public int insertReservationInfo(ReservationInfo reservationInfo){
        Map<String, Object> params = new HashMap<>();
        params.put("product_id", reservationInfo.getProductId());
        params.put("display_info_id", reservationInfo.getDisplayInfoId());
        params.put("user_id", reservationInfo.getUserId());
        params.put("reservation_date", reservationInfo.getReservationDate());
        params.put("create_date",new Date());
        params.put("modify_date",new Date());

        return insertActionWithReservationInfo.executeAndReturnKey(params).intValue();
    }

    // 2. select reservation_info
    public ReservationInfo selectReservationInfo(Integer reservationInfoId) { // 23
        Map<String, Object> params = new HashMap<>();
        params.put("reservationInfoId", reservationInfoId);
        return jdbc.queryForObject(SELECT_RESERVATION_INFO, params, ReservationInfoMapper);
    }

    // 3. reservation_info_price 만들어서 insert
    public int insertReservationInfoPrice(ReservationInfoPrice reservationInfoPrice) {
        Map<String, Object> params = new HashMap<>();
        params.put("reservation_info_id", reservationInfoPrice.getReservationInfoId());
        params.put("product_price_id", reservationInfoPrice.getProductPriceId());
        params.put("count", reservationInfoPrice.getCount());

        return insertActionWithReservationInfoPrice.executeAndReturnKey(params).intValue();

    }

    // 4. select reservation_info_price
    public ReservationInfoPrice selectReservationInfoPrice(Integer reservationInfoPriceId) {
        return jdbc.queryForObject(SELECT_RESERVATION_INFO_PRICE, Collections.singletonMap("reservationInfoPriceId", reservationInfoPriceId), ReservationInfoPriceMapper);
    }




}
