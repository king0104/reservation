package kr.or.connect.comment.service;

import kr.or.connect.comment.dao.CommentDao;
import kr.or.connect.comment.dto.*;
import kr.or.connect.commentimage.dao.CommentImageDao;
import kr.or.connect.commentimage.dto.CommentImage;
import kr.or.connect.fileinfo.dao.FileInfoDao;
import kr.or.connect.fileinfo.dto.FileInfo;
import kr.or.connect.fileinfo.dto.FileInfoRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentDao commentDao;
    private final FileInfoDao fileInfoDao;
    private final CommentImageDao commentImageDao;

    @Transactional
    public List<CommentAndImage> getCommentAndImageList(Integer productId, Integer start) {

        // _1. reservationUserComments 만들기
        List<CommentAndImage> commentAndImageList = new ArrayList<>();

        // (인덱스를 함께 활용하기 위해, listIterator 사용)
        // -- hasnext 거의 안쓰임
        // -- .foreach를 쓰던가 아니면 for each (사실 잘 안쓰임)
        // -- stream으로 할 수 있을 것 같다
        // -- 블럭 안에 블럭이 들어가는 반복문은 -> private 함수로 빼버리는 경우가 많다
        // -- depth를 줄이려고 stream을 사용하는 것임, 그러니까 스트림으로 바꿔보기
        ListIterator<Comment> commentListIterator = commentDao.selectAll(productId, start).listIterator();
        while (commentListIterator.hasNext()) {
            Comment comment = commentListIterator.next();
            ListIterator<CommentImage> commentImageListIterator = commentImageDao.selectAll(comment.getId()).listIterator();

            // _2. reservationUserCommentImages 만들기
            List<CommentImageAndFileInfo> commentImageAndFileInfoList = new ArrayList<>();
            while (commentImageListIterator.hasNext()) {
                CommentImage commentImage = commentImageListIterator.next();
                int fileId = commentImage.getFileId();

                FileInfo fileInfo = fileInfoDao.select(fileId);

                commentImageAndFileInfoList.add(CommentImageAndFileInfo.builder()
                        .id(commentImage.getId())
                        .reservationInfoId(commentImage.getReservationInfoId())
                        .reservationUserCommentId(commentImage.getReservationUserCommentId())
                        .fileId(fileInfo.getFileId())
                        .fileName(fileInfo.getFileName())
                        .saveFileName(fileInfo.getSaveFileName())
                        .contentType(fileInfo.getContentType())
                        .createDate(fileInfo.getCreateDate())
                        .modifyDate(fileInfo.getModifyDate())
                        .build());

            }
            // -- setter 안쓰는 이유. vo. 중간에 변형을 안가할려고 한다. setter를 그래서 빼는 방향으로 간다. 대신 builder사용
            // -- 반복문 사용하는 것
                // -- while문 아예 안쓰임
                // -- for each 써도 .foreach 사용함
            // 하나의 commentAndImage 만들기 - 결과에 추가하기
            commentAndImageList.add(CommentAndImage.builder()
                    .id(comment.getId())
                    .productId(comment.getProductId())
                    .reservationInfoId(comment.getReservationInfoId())
                    .score(comment.getScore())
                    .userId(comment.getUserId())
                    .comment(comment.getComment())
                    .reservationUserCommentImages(commentImageAndFileInfoList)
                    .build());
        }

        return commentAndImageList;

    }

    @Transactional
    public Integer getTotalCount(Integer productId) {
        return commentDao.selectTotalCount(productId);
    }


    @Transactional
    public int register(CommentRegister reservationUserComment, FileInfoRegister fileInfoRegister) {
        // 1. insert into reservation user comment
        int reservationUserCommentId = commentDao.insert(reservationUserComment);

        // 2. insert into fileinfo
        int fileInfoId = fileInfoDao.insert(fileInfoRegister);

        // 3. reservation user comment image 만들기 & 저장
        // - 기본 테이블은 controller에서 만들기
        // - 중간 테이블은 service에서 만들기
        CommentImage commentImage = CommentImage.builder()
                .reservationInfoId(reservationUserComment.getReservationInfoId())
                .reservationUserCommentId(reservationUserCommentId)
                .fileId(fileInfoId).build();

        int reservationUserCommentImageId = commentImageDao.insert(commentImage);

        return 1;
    }

}
