package com.ssafy.bookkoo.bookservice.repository.review;

import com.ssafy.bookkoo.bookservice.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {

    List<Review> findByBookId(Long bookId);

    @Transactional
    void deleteByMemberId(Long memberId);
}
