package kr.or.connect.displayinfo.dao;

import kr.or.connect.config.ApplicationConfig;
import kr.or.connect.promotion.dao.PromotionDao;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {ApplicationConfig.class})
class DisplayInfoDaoTest {


    @Test
    void selectAvgScoreByDisplayInfoId() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        DisplayInfoDao displayInfoDao = ac.getBean(DisplayInfoDao.class);

        int i = displayInfoDao.selectAvgScoreByDisplayInfoId(1);
        System.out.println("i = " + i);
    }
}