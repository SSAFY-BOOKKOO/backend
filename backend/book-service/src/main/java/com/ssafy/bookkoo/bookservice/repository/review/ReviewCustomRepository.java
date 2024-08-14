package com.ssafy.bookkoo.bookservice.repository.review;

import com.ssafy.bookkoo.bookservice.entity.Review;
import java.util.List;

/**
 * 커스텀 리뷰 레포지토리 인터페이스로, 특정 조건을 기반으로 리뷰를 검색하는 메서드를 정의합니다.
 */
public interface ReviewCustomRepository {

    /**
     * 주어진 책 ID에 대한 리뷰 중에서 특정 회원의 리뷰를 제외한 리뷰를 검색합니다.
     *
     * @param memberId 회원 ID
     * @param bookId   책 ID
     * @return 특정 회원의 리뷰를 제외한 리뷰 리스트
     */
    List<Review> findByBookIdExceptMine(Long memberId, Long bookId);

    /**
     * 책에 대해 내가 작성할 수 있는 리뷰는 한 개 이기 떄문에 이거에 대해 작성한 리뷰가 있는지 여부를 확인하는 쿼리
     *
     * @param memberId 회원 ID
     * @param bookId   책 ID
     * @return true/false
     */
    Boolean existsByBookIdAndMine(Long memberId, Long bookId);

    /**
     * memberId, bookId로 리뷰 찾기
     *
     * @param bookId   책 ID
     * @param memberId memberId
     * @return review
     */
    Review findByBookIdMemberId(Long bookId, Long memberId);
}
