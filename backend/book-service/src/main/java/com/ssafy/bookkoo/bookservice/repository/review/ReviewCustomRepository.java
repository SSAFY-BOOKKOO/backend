package com.ssafy.bookkoo.bookservice.repository.review;

import com.ssafy.bookkoo.bookservice.entity.Review;
import java.util.List;

public interface ReviewCustomRepository {

    List<Review> findByBookIdExceptMine(Long memberId, Long bookId);
}
