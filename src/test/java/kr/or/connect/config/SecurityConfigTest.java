package kr.or.connect.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {ApplicationConfig.class,SecurityConfig.class})
class SecurityConfigTest {

    @Test
    void encoder() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class,SecurityConfig.class);
        PasswordEncoder encoder = ac.getBean("encoder", PasswordEncoder.class);
        boolean matches = encoder.matches("1234","$2a$10$G/ADAGLU3vKBd62E6GbrgetQpEKu2ukKgiDR5TWHYwrem0cSv6Z8m");
        System.out.println(matches);

    }
}