package kr.or.connect.promotion.dao;

import kr.or.connect.config.ApplicationConfig;
import kr.or.connect.config.WebAppInitalizer;
import kr.or.connect.promotion.dto.Promotion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {ApplicationConfig.class})
class PromotionDaoTest {

    @Autowired
    PromotionDao promotionDao;

    @Test
    void test() {
        List<Promotion> promotions = promotionDao.selectAll();
        for (Promotion promotion : promotions) {
            System.out.println("promotion = " + promotion);
        }
    }
}