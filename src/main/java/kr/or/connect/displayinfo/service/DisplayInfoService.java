package kr.or.connect.displayinfo.service;

import kr.or.connect.displayinfo.dao.DisplayInfoDao;
import kr.or.connect.displayinfo.dto.DisplayInfoImage;
import kr.or.connect.displayinfo.dto.Product;
import kr.or.connect.displayinfo.dto.ProductImage;
import kr.or.connect.displayinfo.dto.ProductPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DisplayInfoService {

    private static final int All_CATEGORY = 0;
    private final DisplayInfoDao displayInfoDao;

    @Transactional(readOnly = true)
    public List<Product> getProductList(Integer categoryId, Integer start) {
        // - 페이징 사이즈가 몇인지는 보여주는 것이 좋다(컨트롤러든, 서비스든) - dao에 현재 숨어있음...
        // - null이 들어올 수 있으니까 null을 방어하는 것을 해야함 (null 체크하기)
        // - 명확히 값이 넘어와야 한다고 생각되면 무조건 primitive type 사용하기!!(래퍼 타입 무작정 사용 x)
        if (categoryId == All_CATEGORY) {
            return displayInfoDao.selectAllProduct(start);
        }
        return displayInfoDao.selectAllProductByCategoryId(categoryId,start);
    }

    @Transactional(readOnly = true)
    public Integer getTotalCount(Integer categoryId) {
        if (categoryId == All_CATEGORY) {
            return displayInfoDao.selectAllProductCount();
        }
        return displayInfoDao.selectAllProductCountByCategoryId(categoryId);
    }

    /**
     * 전시 정보 구하기
     * - displayInfo라는 새로운 dto를 만드는 것인가? vs 기존 dto별로 따로 넘겨주고 컨트롤러에서 조합해서 내보내는 것인가?
     * - (일단 후자 적용)
     */

    // - read only
    // - 어떤 이점이 있는지 확인해보기 (솔직히 큰 이점은 없음 - 굳이 쓸 필요가 있나?)
    // - 요새는 read only : slave db 이런 식일때만 사용되므로, 단일 db에서는 큰 성능차이 없음
    @Transactional(readOnly = true)
    public Product getProductByDisplayInfoId(Integer displayInfoId) {
        return displayInfoDao.selectProductByDisplayInfoId(displayInfoId);
    }

    @Transactional(readOnly = true)
    public List<ProductImage> getProductImageListByDisplayInfoId(Integer displayInfoId) {
        return displayInfoDao.selectAllProductImageByDisplayInfoId(displayInfoId);
    }

    @Transactional(readOnly = true)
    public List<DisplayInfoImage> getDisplayInfoImageListByDisplayInfoId(Integer displayInfoId) {
        return displayInfoDao.selectAllDisplayInfoImageByDisplayInfoId(displayInfoId);
    }

    @Transactional(readOnly = true)
    public List<ProductPrice> getProductPriceListByDisplayInfoId(Integer displayInfoId) {
        return displayInfoDao.selectAllProductPriceByDisplayInfoId(displayInfoId);
    }

    @Transactional(readOnly = true)
    public Integer getAvgScoreByDisplayInfoId(Integer displayInfoId) {
        return displayInfoDao.selectAvgScoreByDisplayInfoId(displayInfoId);
    }



}
