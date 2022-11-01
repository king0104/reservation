package kr.or.connect.comment.service;

import kr.or.connect.comment.dao.CommentDao;
import kr.or.connect.comment.dto.CommentResponse;
import kr.or.connect.config.ApplicationConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {ApplicationConfig.class})
class CommentResponseServiceTest {


    @Test
    void getCommentList() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        CommentDao commentDao = ac.getBean(CommentDao.class);
        CommentService commentService = ac.getBean(CommentService.class);



    }
}