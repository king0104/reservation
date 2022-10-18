package kr.or.connect.promotion.controller;

import kr.or.connect.promotion.dto.Promotion;
import kr.or.connect.promotion.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PromotionApiController {

    private final PromotionService promotionService;

    @GetMapping("/promotions")
    public Map<String,Object> getPromotions() {
        List<Promotion> promotions = promotionService.getPromotionList();

        Map<String, Object> map = new HashMap<>();
        map.put("size", promotions.size());
        map.put("items", promotions);

        return map;

    }
}
