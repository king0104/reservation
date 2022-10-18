package kr.or.connect.displayinfo.controller;

import kr.or.connect.displayinfo.dto.DisplayInfoImage;
import kr.or.connect.displayinfo.dto.Product;
import kr.or.connect.displayinfo.dto.ProductImage;
import kr.or.connect.displayinfo.dto.ProductPrice;
import kr.or.connect.displayinfo.service.DisplayInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DisplayInfoApiController {

    private final DisplayInfoService displayInfoService;

    @GetMapping("/displayinfos")
    public Map<String,Object> getProducts( // URI 작성은 dash-case
                                            @RequestParam(name = "category-id", required=false, defaultValue= "0") int categoryId,
                                            @RequestParam(name = "start", required = false, defaultValue = "0") int start) {

        List<Product> products = displayInfoService.getProductList(categoryId,start);
        Integer totalCount = displayInfoService.getTotalCount(categoryId);

        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("productCount", products.size());
        map.put("products", products);

        return map;
    }

    /**
     * 전시정보 구하기
     */
    @GetMapping("/displayinfos/{displayId}")
    public Map<String,Object> getDisplayInfos(@PathVariable("displayId") Integer displayInfoId){ // URI 작성은 dash-case
        Product product = displayInfoService.getProductByDisplayInfoId(displayInfoId); // - product 서비스
        List<ProductImage> productImages = displayInfoService.getProductImageListByDisplayInfoId(displayInfoId); // - 조금 더 중요한 매개변수를 가지는 메서드를 by를 안붙임. 예를 들어 pk로 조회하는 경우
        List<DisplayInfoImage> displayInfoImages = displayInfoService.getDisplayInfoImageListByDisplayInfoId(displayInfoId);
        // - 래퍼 타입 : 파라미터 객체는 널이 들어올 수 있어서 자주 함 / - primitive type : 리턴 받는 경우
        Integer avgScore = displayInfoService.getAvgScoreByDisplayInfoId(displayInfoId);
        List<ProductPrice> productPrices = displayInfoService.getProductPriceListByDisplayInfoId(displayInfoId);
        // - map 지양
        Map<String, Object> map = new HashMap<>();
        map.put("product",product);
        map.put("productImages", productImages);
        map.put("displayInfoImages", displayInfoImages);
        map.put("avgScore", avgScore);
        map.put("productPrices", productPrices);

        return map;
    }
}
