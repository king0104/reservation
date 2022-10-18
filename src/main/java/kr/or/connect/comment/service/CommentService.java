package kr.or.connect.comment.service;

import io.swagger.models.auth.In;
import kr.or.connect.comment.dao.CommentDao;
import kr.or.connect.comment.dto.Comment;
import kr.or.connect.comment.dto.ReservationUserCommentImage;
import kr.or.connect.comment.dto.SemiComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentDao commentDao;

    public List<Comment> getCommentList(Integer productId, Integer start) {

        List<ReservationUserCommentImage> reservationUserCommentImageList = commentDao.selectAllReservationUserCommentImage(productId, start);
        // - semicommnet가 아니다, comment로 하기
        List<SemiComment> semiCommentList = commentDao.selectAllSemiComment(productId, start);

        // - db에 4번에서 10번 정도 찌르는 것은 괜찮음
        // - 요새는 for each로 stream으로 씀
        // - for문 잘 안씀(depth 때문에)


        // ruc의 id로 연결되어있음
        List<Comment> commentList = new ArrayList<>();
        for (int i = 0; i < semiCommentList.size(); i++) {

            // 1. ruci 중, 현재 만드려는 comment의 ruc id와 같은 ruc id를 갖고 있는 객체들 찾기
            List<ReservationUserCommentImage> ruciList = new ArrayList<>();
            for (ReservationUserCommentImage reservationUserCommentImage : reservationUserCommentImageList) {
                if (reservationUserCommentImage.getReservationUserCommentId() == semiCommentList.get(i).getReservationInfoId()) {
                    ruciList.add(reservationUserCommentImage);
                }
            }

            // 2. 리턴할 comment dto 만들기(ruci list 포함)
            Comment comment = Comment.builder()
                    .id(semiCommentList.get(i).getId())
                    .productId(semiCommentList.get(i).getProductId())
                    .reservationInfoId(semiCommentList.get(i).getReservationInfoId())
                    .score(semiCommentList.get(i).getScore())
                    .reservationEmail(semiCommentList.get(i).getReservationEmail())
                    .createDate(semiCommentList.get(i).getCreateDate())
                    .modifyDate(semiCommentList.get(i).getModifyDate())
                    .reservationUserCommentImages(ruciList)
                    .build();

            // 3. commentList 만들기
            commentList.add(comment);
            System.out.println(comment);

            // - comment가 잘 만들어지는지 확인 & 잘 만들어지면, 해당 comment의 List에 값 넣기
        }

        return commentList;
    }

    public Integer getTotalCount(Integer productId) {
        return commentDao.selectAllSemiCommentCount(productId);
    }

}
