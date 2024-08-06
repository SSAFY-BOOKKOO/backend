package com.ssafy.bookkoo.bookservice.service.review;

import com.ssafy.bookkoo.bookservice.client.MemberServiceClient;
import com.ssafy.bookkoo.bookservice.dto.other.ResponseMemberInfoDto;
import com.ssafy.bookkoo.bookservice.dto.other.ResponseSurfingMemberInfoDto;
import com.ssafy.bookkoo.bookservice.dto.review.RequestReviewDto;
import com.ssafy.bookkoo.bookservice.dto.review.ResponseReviewDto;
import com.ssafy.bookkoo.bookservice.dto.review.ResponseSurfingReviewDto;
import com.ssafy.bookkoo.bookservice.entity.Book;
import com.ssafy.bookkoo.bookservice.entity.Review;
import com.ssafy.bookkoo.bookservice.entity.ReviewLike;
import com.ssafy.bookkoo.bookservice.entity.ReviewMemberId;
import com.ssafy.bookkoo.bookservice.exception.BookNotFoundException;
import com.ssafy.bookkoo.bookservice.exception.ReviewHasWrittenException;
import com.ssafy.bookkoo.bookservice.exception.ReviewNotFoundException;
import com.ssafy.bookkoo.bookservice.mapper.ReviewMapper;
import com.ssafy.bookkoo.bookservice.repository.book.BookRepository;
import com.ssafy.bookkoo.bookservice.repository.review.ReviewLikeRepository;
import com.ssafy.bookkoo.bookservice.repository.review.ReviewRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * ReviewService의 구현체로, 리뷰 관련 비즈니스 로직을 처리합니다.
 */
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewMapper reviewMapper;
    private final MemberServiceClient memberServiceClient;

    /**
     * 특정 책 ID를 기반으로 리뷰 목록을 조회합니다.
     *
     * @param bookId 책 ID
     * @return 책에 대한 리뷰 응답 DTO 리스트
     */
    @Override
    public List<ResponseReviewDto> getReviewByBookId(Long bookId) {
        List<Review> reviews = reviewRepository.findByBookId(bookId);
        return reviewMapper.toDto(reviews);
    }

    /**
     * 특정 리뷰 ID를 기반으로 리뷰를 조회합니다.
     *
     * @param bookId   책 ID
     * @param reviewId 리뷰 ID
     * @return 조회된 리뷰 응답 DTO
     */
    @Override
    public ResponseReviewDto getReviewById(Long bookId, Long reviewId) {
        Review review = findReviewByIdWithException(reviewId);
        return reviewMapper.toDto(review);
    }

    /**
     * 새로운 리뷰를 추가합니다.
     *
     * @param memberId         회원 ID
     * @param bookId           책 ID
     * @param requestReviewDto 리뷰 요청 DTO
     * @return 생성된 리뷰 응답 DTO
     */
    @Override
    public ResponseReviewDto addReview(
        Long memberId,
        Long bookId,
        RequestReviewDto requestReviewDto
    ) {
        Book book = bookRepository.findById(bookId)
                                  .orElseThrow(BookNotFoundException::new);

        // 만약 이미 만들어진 게 있다면 못 만들게 하기
        if (reviewRepository.existsByBookIdAndMine(memberId, bookId)) {
            throw new ReviewHasWrittenException();
        }

        Review review = Review.builder()
                              .book(book)
                              .memberId(memberId)
                              .content(requestReviewDto.content())
                              .rating(requestReviewDto.rating())
                              .build();
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDto(savedReview);
    }

    /**
     * 특정 리뷰에 대한 좋아요 상태를 토글합니다.
     *
     * @param memberId 회원 ID
     * @param bookId   책 ID
     * @param reviewId 리뷰 ID
     * @return 토글된 좋아요 상태 (true: 좋아요 추가됨, false: 좋아요 제거됨)
     */
    @Override
    public Boolean toggleLikeReview(Long memberId, Long bookId, Long reviewId) {
        Review review = findReviewByIdWithException(reviewId);

        ReviewMemberId reviewMemberId = new ReviewMemberId(reviewId, memberId);
        // 리뷰 좋아요가 이미 있는지 확인
        Optional<ReviewLike> existingReviewLike = reviewLikeRepository.findById(
            reviewMemberId);

        if (existingReviewLike.isPresent()) {
            // 이미 존재하면 삭제 (좋아요 취소)
            reviewLikeRepository.delete(existingReviewLike.get());
            return false; // 좋아요 취소됨
        } else {
            // 존재하지 않으면 저장 (좋아요 추가)
            ReviewLike reviewLike = ReviewLike.builder()
                                              .memberId(memberId)
                                              .review(review)
                                              .build();
            reviewLikeRepository.save(reviewLike);
            return true;
        }
    }

    /**
     * 특정 회원을 제외한 랜덤 리뷰 목록을 조회합니다.
     *
     * @param memberId 회원 ID
     * @param bookId   책 ID
     * @return 회원을 제외한 랜덤 리뷰 응답 DTO 리스트
     */
    @Override
    public List<ResponseSurfingReviewDto> getRandomReviewExceptMine(Long memberId, Long bookId) {
        // 1. 해당 책에 대한 리뷰 목록 불러오기(내가 쓴 리뷰 제외)
        List<Review> reviewsExceptMine = reviewRepository.findByBookIdExceptMine(memberId, bookId);

        // 2. 리뷰가 2개 미만인 경우 모든 리뷰 반환
        if (reviewsExceptMine.size() <= 2) {
            return reviewsExceptMine.stream()
                                    .map(this::mapToSurfingReviewDto)
                                    .collect(Collectors.toList());
        }

        // 3. 리뷰가 2개 이상일 경우 무작위 두개 선택 후 반환
        Collections.shuffle(reviewsExceptMine);
        List<Review> randomReviews = reviewsExceptMine.subList(0, 2);

        return randomReviews.stream()
                            .map(this::mapToSurfingReviewDto)
                            .collect(Collectors.toList());
    }

    /**
     * 리뷰 엔티티를 서핑 리뷰 응답 DTO로 변환합니다.
     *
     * @param review 리뷰 엔티티
     * @return 서핑 리뷰 응답 DTO
     */
    private ResponseSurfingReviewDto mapToSurfingReviewDto(Review review) {
        // 작성자 정보 조회
        ResponseMemberInfoDto memberInfo = memberServiceClient.getMemberInfoById(
            review.getMemberId());

        // 작성자 정보를 포함한 DTO로 변환
        ResponseSurfingMemberInfoDto surfingMemberInfo = ResponseSurfingMemberInfoDto.builder()
                                                                                     .nickName(
                                                                                         memberInfo.nickName())
                                                                                     .profilImgUrl(
                                                                                         memberInfo.profilImgUrl())
                                                                                     .build();

        return ResponseSurfingReviewDto.builder()
                                       .id(review.getId())
                                       .bookId(review.getBook()
                                                     .getId())
                                       .memberId(review.getMemberId())
                                       .content(review.getContent())
                                       .rating(review.getRating())
                                       .likeCount(review.getLikes()
                                                        .size())
                                       .member(surfingMemberInfo)
                                       .build();
    }

    /**
     * 주어진 리뷰 ID로 리뷰를 찾지 못했을 때 예외를 발생시키는 헬퍼 메서드
     *
     * @param reviewId 리뷰 ID
     * @return 조회된 리뷰 엔티티
     */
    private Review findReviewByIdWithException(Long reviewId) {
        return reviewRepository.findById(reviewId)
                               .orElseThrow(ReviewNotFoundException::new);
    }
}
