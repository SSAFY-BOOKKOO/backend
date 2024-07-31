package com.ssafy.bookkoo.bookservice.service.review;

import com.ssafy.bookkoo.bookservice.dto.review.RequestReviewDto;
import com.ssafy.bookkoo.bookservice.dto.review.ResponseReviewDto;
import com.ssafy.bookkoo.bookservice.entity.Book;
import com.ssafy.bookkoo.bookservice.entity.Review;
import com.ssafy.bookkoo.bookservice.entity.ReviewLike;
import com.ssafy.bookkoo.bookservice.entity.ReviewMemberId;
import com.ssafy.bookkoo.bookservice.exception.BookNotFoundException;
import com.ssafy.bookkoo.bookservice.exception.ReviewNotFoundException;
import com.ssafy.bookkoo.bookservice.mapper.ReviewMapper;
import com.ssafy.bookkoo.bookservice.repository.book.BookRepository;
import com.ssafy.bookkoo.bookservice.repository.review.ReviewLikeRepository;
import com.ssafy.bookkoo.bookservice.repository.review.ReviewRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewMapper reviewMapper;
    private final BookRepository bookRepository;
    private final ReviewLikeRepository reviewLikeRepository;


    @Override
    public List<ResponseReviewDto> getReviewByBookId(Long bookId) {
        List<Review> reviews = reviewRepository.findByBookId(bookId);
        return reviewMapper.toDto(reviews);
    }

    @Override
    public ResponseReviewDto getReviewById(Long bookId, Long reviewId) {
        Review review = findReviewByIdWithException(reviewId);
        return reviewMapper.toDto(review);
    }

    @Override
    public ResponseReviewDto addReview(
        Long memberId,
        Long bookId,
        RequestReviewDto requestReviewDto
    ) {
        Book book = bookRepository.findById(bookId)
                                  .orElseThrow(BookNotFoundException::new);
        Review review = Review.builder()
                              .book(book)
                              .memberId(memberId)
                              .content(requestReviewDto.content())
                              .rating(requestReviewDto.rating())
                              .build();
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDto(savedReview);
    }

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

    private Review findReviewByIdWithException(Long reviewId) {

        return reviewRepository.findById(reviewId)
                               .orElseThrow(ReviewNotFoundException::new);
    }
}
