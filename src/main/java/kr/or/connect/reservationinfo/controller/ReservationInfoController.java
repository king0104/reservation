package kr.or.connect.reservationinfo.controller;

import kr.or.connect.reservationinfo.dto.*;
import kr.or.connect.reservationinfo.service.ReservationInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReservationInfoController {

    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy.MM.dd");
    private final ReservationInfoService reservationInfoService;

    @PostMapping("/reservationInfos")
    public ReservationInfoSaveResponse addReservationInfo(@RequestBody ReservationInfoSaveRequest request) throws ParseException {

        // 1. request -> ri 만들기
        ReservationInfo reservationInfo = ReservationInfo.builder()
                .productId(request.getProductId())
                .displayInfoId(request.getDisplayInfoId())
                .userId(request.getUserId())
                .reservationDate(transFormat.parse(request.getReservationYearMonthDay())) // - str to date 함수 : mysql에 함수가 있음
                .build();

        // 2. request -> rips 만들기
        List<PriceRequest> priceRequests = request.getPrices();

        return reservationInfoService.register(reservationInfo, priceRequests);

    }


}
