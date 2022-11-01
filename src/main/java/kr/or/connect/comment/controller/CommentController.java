package kr.or.connect.comment.controller;

import kr.or.connect.comment.dto.*;
import kr.or.connect.comment.service.CommentService;
import kr.or.connect.fileinfo.dto.FileInfoRegister;
import kr.or.connect.reservationinfo.service.ReservationInfoService;
import kr.or.connect.security.dto.CustomUserDetails;
import kr.or.connect.user.service.UserTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ReservationInfoService reservationInfoService;
    private final UserTableService userTableService;

    @GetMapping("/api/comments")
    public CommentResponse getCommentAndImages(
            @RequestParam(name = "product-id",required = false) Integer productId,
            @RequestParam(name = "start",required = false, defaultValue = "0") Integer start) {
        /**
         * api 재설계
         * 1. product id로 reservation user comments 가져옴 - selectAllLimit5(?)
         * 1-1. 이때, limit 5로 해서 가져온다
         * 1. product id로 reservation user commnet의 갯수 가져옴 - selectCount
         * 1-1 쿼리문에 COUNT() 사용해서 가져온다
         *
         *
         * 2. reservation user comments id로 reservation user comment images 가져옴
         *
         * 3. response 만들기
         * // 컨트롤러에서 추가
         * 3-1. totalcount
         * 3-2. commentcount
         *
         * // 서비스에서 만들어서 받기
         * 3-3. reservation user comments = 1번이랑 2번 합쳐서 넣어주기
         */

        Integer totalCount = commentService.getTotalCount(productId);
        List<CommentAndImage> commentAndImages = commentService.getCommentAndImageList(productId, start);

        return CommentResponse.builder()
                .totalCount(totalCount)
                .commentCount(commentAndImages.size())
                .commentAndImages(commentAndImages).build();

    }

    // -- 컨트롤러에서 너무 비즈니스 로직이 있다.
    @PostMapping("/api/comments")
    public CommentRegisterResponse register(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                            @RequestParam (value = "reservationInfoId") int reservationInfoId,
                                            @RequestParam (value = "score") int score,
                                            @RequestParam (value = "comment") String comment,
                                            @RequestPart (value = "multipartFile") MultipartFile multipartFile) {

        // 1. reservation user comment 만들기
        // product id, user id 가져오기
        // - 위에 파라미터에 customuserdetail 맞는지 확인


        // -- 비즈니스 로직
        // -- 아래 쪽은 전부다 서비스로 빼기
        int productId = reservationInfoService.getProductId(reservationInfoId);
        int userId = userTableService.getUserId(customUserDetails.getUsername());

        CommentRegister reservationUserComment = CommentRegister.builder()
                .productId(productId)
                .reservationInfoId(reservationInfoId)
                .userId(userId)
                .score(score)
                .comment(comment)
                .build();

        // 2. file info 만들기

        // -- db에 정보만 저장하고 file
        // -- 파일을 받아서 로컬 스토리지에 저장해야한다
        // -- getResource 이런 것 이용
        // -- fileutil 사용
        String originalFilename = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        String saveFileName = "/Users/yoon/web-backend/" + originalFilename; // -- 상수는 빼기 // -- 환경에 따라 변할 수 있는 상수는 properties에 빼둔다

        FileInfoRegister fileInfoRegister = FileInfoRegister.builder()
                .fileName(originalFilename)
                .saveFileName(saveFileName)
                .contentType(contentType)
                .deleteFlag(0).build();

        commentService.register(reservationUserComment, fileInfoRegister);

        // response 만들기
        return CommentRegisterResponse.builder()
                .result("success") // -- 상수는 변수로 빼는 것이 맞다
                .productId(productId)
                .build();
    }


}
