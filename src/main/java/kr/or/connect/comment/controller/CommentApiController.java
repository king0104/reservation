package kr.or.connect.comment.controller;

import kr.or.connect.comment.dto.Comment;
import kr.or.connect.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentApiController {
    private final CommentService commentService;

    // - rest api 가져가는 방향 찾아보기
    // - 공통 접두어를 안뺴는 방향으로 - 로그 복붙할라고
    @GetMapping("/comments")
    public Map<String,Object> getComments(
            @RequestParam(name = "product-id",required = false) Integer productId,
            @RequestParam(name = "start",required = false, defaultValue = "0") Integer start) {

        List<Comment> comments = commentService.getCommentList(productId, start); // null이면, 전체 프로덕트
        Integer totalCount = commentService.getTotalCount(productId);

        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("commentCount", comments.size());
        map.put("reservationUserComments", comments);

        return map;

    }


}
