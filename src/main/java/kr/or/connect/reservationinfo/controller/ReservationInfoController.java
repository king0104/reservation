package kr.or.connect.reservationinfo.controller;

import kr.or.connect.reservationinfo.dto.*;
import kr.or.connect.reservationinfo.service.ReservationInfoService;
import kr.or.connect.security.dto.CustomUserDetails;
import kr.or.connect.user.service.UserTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationInfoController {

    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy.MM.dd");
    private final ReservationInfoService reservationInfoService;
    private final UserTableService userTableService;

    @PostMapping("/api/reservationInfos")
    public ReservationInfoSaveResponse register(@RequestBody ReservationInfoSaveRequest request) throws ParseException {

        // 1. request -> ri 만들기
        ReservationInfo reservationInfo = ReservationInfo.builder()
                .productId(request.getProductId())
                .displayInfoId(request.getDisplayInfoId())
                .userId(request.getUserId())
                .reservationDate(transFormat.parse(request.getReservationYearMonthDay())) // - str to date 함수 : mysql에 함수가 있음
                .cancelFlag(0)
                .build();

        // 2. request -> rips 만들기
        List<PriceRequest> priceRequests = request.getPrices();

        return reservationInfoService.register(reservationInfo, priceRequests);

    }

    @GetMapping("/api/reservationInfos")
    public ReservationInfoAndProductResponse get(@AuthenticationPrincipal CustomUserDetails customUser) {

        // 1. 현재 로그인 정보 가져오기 - username으로 userid 가져오기
        int userId = userTableService.getUserId(customUser.getUsername());
        List<ReservationInfoAndProduct> reservationInfoAndProducts = reservationInfoService.get(userId);


        // response 만들기
        ReservationInfoAndProductResponse reservationInfoAndProductResponse = ReservationInfoAndProductResponse.builder()
                .size(reservationInfoAndProducts.size())
                .items(reservationInfoAndProducts)
                .build();

        System.out.println(reservationInfoAndProductResponse);


        return reservationInfoAndProductResponse;

    }

    @PutMapping("/api/reservationInfos")
    public ReservationInfoUpdateResponse update(@RequestBody ReservationInfoUpdateRequest request) {
        // 1. request -> id 뽑기 -> 메서드 실행
        int cnt = reservationInfoService.updateCancelFlag(request.getId());

        // 2. result 결정하기
        String result = "success";
        if (cnt == 0) {
            result = "fail";
        }

        return ReservationInfoUpdateResponse.builder()
                .result(result)
                .build();

    }


}
