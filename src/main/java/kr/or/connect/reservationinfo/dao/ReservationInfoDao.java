package kr.or.connect.reservationinfo.dao;

import kr.or.connect.reservationinfo.dto.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ReservationInfoDao {

    private static final String INSERT_RESERVATION_INFO =
           "INSERT INTO reservation_info(product_id, display_info_id, user_id, reservation_date, cancel_flag, create_date, modify_date)\n" +
                   "VALUES (:product_id, :display_info_id, :user_id, :reservation_date, :cancel_flag, NOW(), NOW())";

    private static final String INSERT_RESERVATION_INFO_PRICE =
            "INSERT INTO reservation_info_price(reservation_info_id, product_price_id, count)\n" +
                    "VALUES (:reservation_info_id, :product_price_id, :count)";

    private static final String SELECT_RESERVATION_INFO =
            "SELECT id, product_id, display_info_id, user_id, reservation_date, cancel_flag, create_date, modify_date\n" +
                    "FROM reservation_info\n" +
                    "WHERE id = :reservationInfoId";

    private static final String SELECT_RESERVATION_INFO_PRICE =
            "SELECT id, reservation_info_id, product_price_id, count\n" +
                    "FROM reservation_info_price\n" +
                    "WHERE id = :reservationInfoPriceId";

    private static final String SELECT_ALL_RESERVATION_INFO_READ_RESPONSE =
            "SELECT ri.id, ri.product_id, ri.display_info_id, ri.cancel_flag ,p.description as product_description, p.content as product_content, ri.user_id, SUM(pp.price) as sum_price, ri.reservation_date , ri.create_date, ri.modify_date\n" +
                    "FROM reservation_info ri\n" +
                    "INNER JOIN product p ON ri.product_id = p.id\n" +
                    "INNER JOIN reservation_info_price rip ON ri.id = rip.reservation_info_id\n" +
                    "INNER JOIN product_price pp ON rip.product_price_id = pp.id\n" +
                    "WHERE ri.user_id = :userId\n" +
                    "GROUP BY ri.id";

    private static final String UPDATE_CANCEL_FLAG = // -- 서비스에서 매개변수를 받는 것이 맞다. dao에서는 값을 박는 로직이 없는 것임. 서비스에서 그런 비즈니스적 로직을 진행하는 것임 // -- modify date를 업데이트 안한다
            "UPDATE reservation_info \n" +
                    "SET cancel_flag = 1 \n" +
                    "WHERE id =:id";

    private static final String SELECT_PRODUCT_ID =
            "SELECT product_id \n"+
                    "FROM reservation_info \n"+
                    "WHERE id =:id";

//    왜 이 코드는 안되는지
//    private final DataSource dataSource;
//    private NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);

    private NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsertOperations insertActionWithReservationInfoPrice;
    private RowMapper<ReservationInfo> ReservationInfoMapper = BeanPropertyRowMapper.newInstance(ReservationInfo.class);
    private RowMapper<ReservationInfoPrice> ReservationInfoPriceMapper = BeanPropertyRowMapper.newInstance(ReservationInfoPrice.class);
    private RowMapper<ReservationInfoAndProduct> ReservationInfoAndProductMapper = BeanPropertyRowMapper.newInstance(ReservationInfoAndProduct.class);

    public ReservationInfoDao(DataSource dataSource) {
//        jdbc.update() // - 쿼리 유연함
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

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
        params.put("cancel_flag", reservationInfo.getCancelFlag());
        params.put("create_date",new Date());
        params.put("modify_date",new Date());

        KeyHolder reservationInfoKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_RESERVATION_INFO, new MapSqlParameterSource(params), reservationInfoKeyHolder, new String[]{"id"});
        return reservationInfoKeyHolder.getKey().intValue();
    }

    // 2. select reservation_info
    public ReservationInfo selectReservationInfo(int reservationInfoId) { // 23
        Map<String, Object> params = new HashMap<>();
        params.put("reservationInfoId", reservationInfoId);
        return jdbcTemplate.queryForObject(SELECT_RESERVATION_INFO, params, ReservationInfoMapper);
    }

    // 3. reservation_info_price 만들어서 insert
    public int insertReservationInfoPrice(ReservationInfoPrice reservationInfoPrice) {
        Map<String, Object> params = new HashMap<>();
        params.put("reservation_info_id", reservationInfoPrice.getReservationInfoId());
        params.put("product_price_id", reservationInfoPrice.getProductPriceId());
        params.put("count", reservationInfoPrice.getCount());

        KeyHolder reservationInfoPriceKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_RESERVATION_INFO_PRICE, new MapSqlParameterSource(params), reservationInfoPriceKeyHolder, new String[]{"id"});
        return reservationInfoPriceKeyHolder.getKey().intValue();

    }

    // 4. select reservation_info_price
    public ReservationInfoPrice selectReservationInfoPrice(int reservationInfoPriceId) {
        return jdbcTemplate.queryForObject(SELECT_RESERVATION_INFO_PRICE, Collections.singletonMap("reservationInfoPriceId", reservationInfoPriceId), ReservationInfoPriceMapper);
    }


    // select reservation info read response
    public List<ReservationInfoAndProduct> selectAllReservationInfoReadResponse(Integer userId) {
        return jdbcTemplate.query(SELECT_ALL_RESERVATION_INFO_READ_RESPONSE, Collections.singletonMap("userId",userId), ReservationInfoAndProductMapper);
    }

    // 예약 취소 api
    // n건 수정되었다고 리턴해주는 것
    public int updateCancelFlag(int id) {
        return jdbcTemplate.update(UPDATE_CANCEL_FLAG,Collections.singletonMap("id",id));
    }

    public int selectProductId(int id) {
        return jdbcTemplate.queryForObject(SELECT_PRODUCT_ID, Collections.singletonMap("id", id), Integer.class);
    }

}
