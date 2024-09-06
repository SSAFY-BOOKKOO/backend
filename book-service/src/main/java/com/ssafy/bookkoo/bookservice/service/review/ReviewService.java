package com.ssafy.bookkoo.bookservice.service.review;

import com.ssafy.bookkoo.bookservice.dto.review.RequestReviewDto;
import com.ssafy.bookkoo.bookservice.dto.review.ResponseReviewDto;
import com.ssafy.bookkoo.bookservice.dto.review.ResponseSurfingReviewDto;
import java.util.List;

/**
 * 리뷰 서비스 인터페이스로, 리뷰 관련 비즈니스 로직을 정의합니다.
 */
public interface ReviewService {

    /**
     * 특정 책 ID를 기반으로 리뷰 목록을 조회합니다.
     *
     * @param bookId 책 ID
     * @return 책에 대한 리뷰 응답 DTO 리스트
     */
    List<ResponseReviewDto> getReviewByBookId(Long bookId);

    /**
     * 특정 리뷰 ID를 기반으로 리뷰를 조회합니다.
     *
     * @param bookId   책 ID
     * @param memberId 사용자 ID
     * @return 조회된 리뷰 응답 DTO
     */
    ResponseReviewDto getMyReview(Long bookId, Long memberId);

    /**
     * 특정 리뷰 ID를 기반으로 리뷰를 조회합니다.
     *
     * @param bookId   책 ID
     * @param reviewId 리뷰 ID
     * @return 조회된 리뷰 응답 DTO
     */
    ResponseReviewDto getReviewById(Long bookId, Long reviewId);

    /**
     * 새로운 리뷰를 추가합니다.
     *
     * @param memberId         회원 ID
     * @param bookId           책 ID
     * @param requestReviewDto 리뷰 요청 DTO
     * @return 생성된 리뷰 응답 DTO
     */
    ResponseReviewDto addReview(Long memberId, Long bookId, RequestReviewDto requestReviewDto);

    /**
     * 특정 회원을 제외한 랜덤 리뷰 목록을 조회합니다.
     *
     * @param memberId 회원 ID
     * @param bookId   책 ID
     * @return 회원을 제외한 랜덤 리뷰 응답 DTO 리스트
     */
    List<ResponseSurfingReviewDto> getRandomReviewExceptMine(Long memberId, Long bookId);

    /**
     * 사용자 탈퇴시 사용자의 한줄평, 좋아요 삭제
     *
     * @param memberId 사용자 id
     */
    void deleteReviewsByMemberId(Long memberId);

    /**
     * 한줄평 수정
     *
     * @param memberId 멤버 ID
     * @param bookId   책 ID
     * @param reviewId 한줄평 ID
     * @param dto      바꿀 값
     * @return ResponseReviewDto
     */
    ResponseReviewDto updateReview(Long memberId, Long bookId, Long reviewId, RequestReviewDto dto);

    /**
     * 리뷰 삭제
     *
     * @param memberId 멤버 ID
     * @param bookId   책 ID
     * @param reviewId 한줄평 ID
     */
    void deleteReviewById(Long memberId, Long bookId, Long reviewId);
}
