package kr.or.connect.displayinfo.dao;

public class DisplayInfoDaoSqls {
    public static final String SELECT_All_PRODUCT =
            // INNER JOIN문 작성하기
            // rowMapper 사용하므로, 컬럼명 맞춰줘야 한다
            "SELECT di.id, c.id category_id ,di.id display_info_id, c.name, p.description,p.content,p.event, di.opening_hours, di.place_name, di.place_street, di.tel, di.homepage, di.email, di.create_date, di.modify_date,pi.file_id\n" +
                    "FROM display_info di\n" +
                    "INNER JOIN product p ON di.product_id = p.id\n" +
                    "INNER JOIN category c ON c.id = p.category_id\n" +
                    "INNER JOIN product_image pi ON p.id = pi.product_id\n" +
                    "WHERE pi.type = 'ma'\n" +
                    "LIMIT 4 OFFSET :start";

    public static final String SELECT_All_PRODUCT_BY_CATEGORY_ID =
            // INNER JOIN문 작성하기
            // rowMapper 사용하므로, 컬럼명 맞춰줘야 한다
            // - mysql optimizer 알고 있으면 좋을 것 같다.
            // - from절은 "driving table" - 작은 애로 - category
            // - 만약 product에 조건이 달려서 product가 적어지면, driving table이 product table
            "SELECT di.id, c.id category_id ,di.id display_info_id, c.name, p.description,p.content,p.event, di.opening_hours, di.place_name, di.place_street, di.tel, di.homepage, di.email, di.create_date, di.modify_date,pi.file_id\n" +
                    "FROM display_info di\n" +
                    "INNER JOIN product p ON di.product_id = p.id\n" +
                    "INNER JOIN category c ON c.id = p.category_id\n" +
                    "INNER JOIN product_image pi ON p.id = pi.product_id\n" +
                    "WHERE c.id = :categoryId AND pi.type = 'ma'\n" +
                    "LIMIT 4 OFFSET :start";

    public static final String SELECT_All_PRODUCT_COUNT =
            // INNER JOIN문 작성하기
            // rowMapper 사용하므로, 컬럼명 맞춰줘야 한다
            // - COUNT(*) & COUNT(컬럼) & COUNT(1) 차이점 찾아보기
            "SELECT COUNT(*)\n" +
                    "FROM display_info di\n" +
                    "INNER JOIN product p ON di.product_id = p.id\n" +
                    "INNER JOIN category c ON c.id = p.category_id\n" +
                    "INNER JOIN product_image pi ON p.id = pi.product_id\n" +
                    "WHERE pi.type = 'ma'";

    public static final String SELECT_All_PRODUCT_COUNT_BY_CATEGORY_ID =
            // INNER JOIN문 작성하기
            // rowMapper 사용하므로, 컬럼명 맞춰줘야 한다
            // - mysql optimizer 알고 있으면 좋을 것 같다.
            // - from절은 "driving table" - 작은 애로 - category
            // - 만약 product에 조건이 달려서 product가 적어지면, driving table이 product table
            "SELECT COUNT(*)\n" +
                    "FROM display_info di\n" +
                    "INNER JOIN product p ON di.product_id = p.id\n" +
                    "INNER JOIN category c ON c.id = p.category_id\n" +
                    "INNER JOIN product_image pi ON p.id = pi.product_id\n" +
                    "WHERE c.id = :categoryId AND pi.type = 'ma'";



    public static final String SELECT_PRODUCT_BY_DISPLAY_INFO_ID =
            // INNER JOIN문 작성하기
            // rowMapper 사용하므로, 컬럼명 맞춰줘야 한다
            "SELECT di.id, c.id category_id ,di.id display_info_id, c.name, p.description,p.content,p.event, di.opening_hours, di.place_name, di.place_street, di.tel, di.homepage, di.email, di.create_date, di.modify_date,pi.file_id\n" +
                    "FROM display_info di\n" +
                    "INNER JOIN product p ON di.product_id = p.id\n" +
                    "INNER JOIN category c ON c.id = p.category_id\n" +
                    "INNER JOIN product_image pi ON p.id = pi.product_id\n" +
                    "WHERE pi.type = 'ma' AND di.id = :displayInfoId";

    public static final String SELECT_ALL_PRODUCT_IMAGE_BY_DISPLAY_INFO_ID =
            "SELECT pi.product_id, pi.id AS product_image_id, type, fi.id AS file_info_id , file_name, save_file_name, content_type, delete_flag, fi.create_date, fi.modify_date\n" +
                    "FROM product_image pi\n" +
                    "INNER JOIN file_info fi ON pi.file_id = fi.id\n" +
                    "INNER JOIN product p ON p.id = pi.product_id\n" +
                    "INNER JOIN display_info di ON p.id = di.product_id\n" +
                    "WHERE di.id = :displayInfoId AND pi.type = 'ma'";

    public static final String SELECT_ALL_DISPLAY_INFO_IMAGE_BY_DISPLAY_INFO_ID =
            "SELECT dii.id, display_info_id, file_id, file_name, save_file_name, content_type, delete_flag, fi.create_date, fi.modify_date\n" +
                    "FROM display_info_image dii\n" +
                    "INNER JOIN file_info fi ON fi.id = dii.file_id\n" +
                    "INNER JOIN display_info di ON dii.display_info_id = di.id\n" +
                    "WHERE di.id = :displayInfoId";

    public static final String SELECT_ALL_PRODUCT_PRICE_BY_DISPLAY_INFO_ID =
            // - table alias 작성할 때는, 단어로 작성하는 편이다. 완전 약어가 아니라, product_price -> price 이런 식으로
            // - 나중에 내가 봐도 모를 수 있음
            "SELECT pp.id AS id, pp.product_id AS product_id, pp.price_type_name, pp.price, pp.discount_rate, pp.create_date, pp.modify_date\n" +
                    "FROM product_price pp\n" +
                    "INNER JOIN display_info di ON pp.product_id = di.product_id\n" +
                    "WHERE di.id = :displayInfoId\n" +
                    "ORDER BY pp.id DESC";

    public static final String SELECT_AVG_SCORE_BY_DISPLAY_INFO_ID =
            "SELECT AVG(ruc.score)\n" +
                    "FROM display_info di\n" +
                    "INNER JOIN reservation_info ri ON di.id = ri.display_info_id\n" +
                    "INNER JOIN reservation_user_comment ruc ON ri.id = ruc.reservation_info_id\n" +
                    "WHERE di.id = :displayInfoId\n" +
                    "GROUP BY di.id";

}
