package kr.or.connect.reservationinfo.dao;

import kr.or.connect.config.ApplicationConfig;
import kr.or.connect.config.WebAppInitalizer;
import kr.or.connect.reservationinfo.dto.ReservationInfo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {ApplicationConfig.class})
class ReservationInfoDaoTest {

    @Autowired
    ReservationInfoDao reservationInfoDao;

    @Test
    void insert() {
//        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
//        ReservationInfoDao reservationInfoDao = ac.getBean(ReservationInfoDao.class);

        ReservationInfo reservationInfo = ReservationInfo.builder()
                .productId(100L)
                .displayInfoId(100L)
                .userId(100L)
                .reservationDate(new Date())
                .build();

        ReservationInfo reservationInfo1 = reservationInfoDao.selectReservationInfo(1);

        System.out.println("reservationInfo1 = " + reservationInfo1);
    }

}