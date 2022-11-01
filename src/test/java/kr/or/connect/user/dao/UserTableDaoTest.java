package kr.or.connect.user.dao;

import kr.or.connect.config.ApplicationConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {ApplicationConfig.class})
class UserTableDaoTest {

    @Autowired
    UserTableDao userTableDao;

    @Test
    void selectUserId() {
        Integer userId = userTableDao.selectUserId("yoon@naver.com");
        System.out.println(userId);

    }
}