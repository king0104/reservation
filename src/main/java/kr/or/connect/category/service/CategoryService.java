package kr.or.connect.category.service;

import kr.or.connect.category.dao.CategoryDao;
import kr.or.connect.category.dto.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
// - 스프링 삼각형
// - pojo ioc aop
// - AOP, Proxy Pattern, Proxy 객체, AsectJ, Dynamic Proxy, CGLIB 개념 반드시 알기
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryDao categoryDao;

    @Transactional(readOnly = true)
    public List<Category> getCategoryList(){
        List<Category> categoryList = categoryDao.selectAll();
        return categoryList;
    }

}
