package kr.or.connect.displayinfo.dao;

import kr.or.connect.displayinfo.dto.DisplayInfoImage;
import kr.or.connect.displayinfo.dto.Product;
import kr.or.connect.displayinfo.dto.ProductImage;
import kr.or.connect.displayinfo.dto.ProductPrice;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kr.or.connect.displayinfo.dao.DisplayInfoDaoSqls.*;
import static kr.or.connect.displayinfo.dao.DisplayInfoDaoSqls.SELECT_ALL_PRODUCT_PRICE_BY_DISPLAY_INFO_ID;

@Repository
public class DisplayInfoDao {

    private NamedParameterJdbcTemplate jdbc; // - jdbcTemplate으로 명칭 바꾸기
    private RowMapper<Product> productRowMapper = BeanPropertyRowMapper.newInstance(Product.class);
    private RowMapper<ProductImage> productImageRowMapper = BeanPropertyRowMapper.newInstance(ProductImage.class);
    private RowMapper<DisplayInfoImage> displayInfoImageRowMapper = BeanPropertyRowMapper.newInstance(DisplayInfoImage.class);
    private RowMapper<ProductPrice> productPriceRowMapper = BeanPropertyRowMapper.newInstance(ProductPrice.class);

    // 스프링 4.0 이상부터는, 객체가 Bean 객체라면 Autowired 없이도 DI 주입이된다.
    public DisplayInfoDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    // - jdbcTemplate도 빈으로 만들자
    // - 생성자 주입 방식 : 상호 참조를 막아준다! - springboot에서는 상호참조시 익셉션 터트림

    /**
     * 상품 목록 구하기
     */
    public List<Product> selectAllProduct(Integer start) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);

        return jdbc.query(SELECT_All_PRODUCT, params, productRowMapper);
    }

    // - ByCategoryId
    // - jpa때문에 생긴 것 같은 느낌. 자바의 메서드명 - 행위, 파라미터 - 뭐가 필요한지, 리턴 - 결과. 다형성을 무시하는 느낌이랄까..
    // - 클래스를 많이 생성하지 않는, 담백하게
    // -- 근데, 아래 displayinfo로 select하는 경우에 메서드 명이 겹쳐버림...
    public List<Product> selectAllProductByCategoryId (Integer categoryId, Integer start) {
        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("start", start);

        return jdbc.query(SELECT_All_PRODUCT_BY_CATEGORY_ID, params, productRowMapper);
    }

    public Integer selectAllProductCount() {
        return jdbc.queryForObject(SELECT_All_PRODUCT_COUNT, Collections.emptyMap(), Integer.class);
    }

    public Integer selectAllProductCountByCategoryId (Integer categoryId) {
        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);

        return jdbc.queryForObject(SELECT_All_PRODUCT_COUNT_BY_CATEGORY_ID, params, Integer.class);
    }


    /**
     * 전시정보 구하기
     */
    public Product selectProductByDisplayInfoId (Integer displayInfoId) {
        Map<String, Object> params = new HashMap<>();
        params.put("displayInfoId", displayInfoId);

        return jdbc.queryForObject(SELECT_PRODUCT_BY_DISPLAY_INFO_ID, params, productRowMapper);
    }


    public List<ProductImage> selectAllProductImageByDisplayInfoId (Integer displayInfoId) {
        Map<String, Object> params = new HashMap<>();
        params.put("displayInfoId", displayInfoId);

        return jdbc.query(SELECT_ALL_PRODUCT_IMAGE_BY_DISPLAY_INFO_ID, params, productImageRowMapper);
    }

    public List<DisplayInfoImage> selectAllDisplayInfoImageByDisplayInfoId (Integer displayInfoId) {
        Map<String, Object> params = new HashMap<>();
        params.put("displayInfoId", displayInfoId);

        return jdbc.query(SELECT_ALL_DISPLAY_INFO_IMAGE_BY_DISPLAY_INFO_ID, params, displayInfoImageRowMapper);
    }

    public List<ProductPrice> selectAllProductPriceByDisplayInfoId (Integer displayInfoId) {
        Map<String, Object> params = new HashMap<>();
        params.put("displayInfoId", displayInfoId);

        return jdbc.query(SELECT_ALL_PRODUCT_PRICE_BY_DISPLAY_INFO_ID, params, productPriceRowMapper);
    }

    public int selectAvgScoreByDisplayInfoId (Integer displayInfoId) {
        Map<String, Object> params = new HashMap<>();
        params.put("displayInfoId", displayInfoId);

        return jdbc.queryForObject(SELECT_AVG_SCORE_BY_DISPLAY_INFO_ID, params, Integer.class);

    }









}
