package kr.or.connect.category.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.or.connect.category.dto.Category;
import kr.or.connect.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Api(value = "카테고리 컨트롤러")
public class CategoryApiController {

    private final CategoryService categoryService;

    @ApiOperation(value = "카테고리 목록 조회")
    @ApiResponses({
            @ApiResponse(code=200, message="정상 조회"),
            @ApiResponse(code=404, message="주소 오류"),
            @ApiResponse(code=500, message="내부 서버 오류")
    })
    @GetMapping("/categories")
    public Map<String, Object> getCategorys() {
        List<Category> categories = categoryService.getCategoryList();

        Map<String, Object> map = new HashMap<>();
        map.put("size", categories.size());
        map.put("items", categories);

        return map;
        // map은 틀이 너무 자유롭다
        // 보통 클래스를 만들어서 던진다
    }
}
