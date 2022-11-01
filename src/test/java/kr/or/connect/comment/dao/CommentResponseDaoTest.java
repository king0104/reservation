package kr.or.connect.comment.dao;

import kr.or.connect.config.ApplicationConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {ApplicationConfig.class})
class CommentResponseDaoTest {

    @Test
    void selectAll() {

    }

}