package kr.or.connect.promotion.service;

import kr.or.connect.promotion.dao.PromotionDao;
import kr.or.connect.promotion.dto.Promotion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionService{
    private final PromotionDao promotionDao;

    public List<Promotion> getPromotionList() {
        return promotionDao.selectAll();
    }

}
