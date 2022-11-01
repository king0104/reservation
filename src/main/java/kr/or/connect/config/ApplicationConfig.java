package kr.or.connect.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {
        "kr.or.connect.category.service," +
        "kr.or.connect.comment.service," +
        "kr.or.connect.displayinfo.service," +
        "kr.or.connect.promotion.service," +
        "kr.or.connect.security.service," +
        "kr.or.connect.reservationinfo.service," +
        "kr.or.connect.user.service," +
        "kr.or.connect.fileinfo.service," +
        "kr.or.connect.category.dao," +
        "kr.or.connect.comment.dao," +
        "kr.or.connect.commentimage.dao," +
        "kr.or.connect.displayinfo.dao," +
        "kr.or.connect.promotion.dao," +
        "kr.or.connect.security.dao," +
        "kr.or.connect.reservationinfo.dao," +
        "kr.or.connect.user.dao," +
        "kr.or.connect.fileinfo.dao"
        })
@Import({DBConfig.class})
public class ApplicationConfig {

}
