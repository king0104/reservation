package kr.or.connect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * DispatcherServlet이 읽어들일 설정 파일!!!!!!!!!!!
 */
@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan(basePackages = {
        "kr.or.connect.category.controller," +
        "kr.or.connect.comment.controller," +
        "kr.or.connect.displayinfo.controller," +
        "kr.or.connect.promotion.controller," +
        "kr.or.connect.security.controller," +
        "kr.or.connect.reservationinfo.controller"
})
@Import({SwaggerConfig.class})
public class WebMvcContextConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
    }

    // default servlet handler를 사용하게 합니다.
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 컨트롤러 클래스 없이 특정 view에대한 컨트롤러를 추가할 수 있다.
     *
     * 기능 : "/"라고 요청이 들어오면 "index"라는 이름을 리턴하는 RestController를 만들게 된다.
     * 따라서, /라고 요청이 들어오면, index라는 뷰 이름을 리턴한다. (뷰 이름 리턴한다는 것이 mvc 구조의 특징임)
     */
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        System.out.println("addViewControllers가 호출됩니다. ");
        registry.addViewController("/").setViewName("index");
    }

    /**
     * <직접 뷰 리졸버를 등록한 것이다!!!>
     * 뷰 이름만 적어주더라도 알맞은 view를 찾아가도록 하는 것
     * https://galid1.tistory.com/527?category=783055
     *
     * 기능 : 컨트롤러에서 리턴하는 뷰 이름에 접두/접미어를 붙여서 jsp페이지의 경로를 찾는다.
     */
    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }



}