package com.ssafy.bookkoo.bookservice.repository.review;

import com.ssafy.bookkoo.bookservice.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByBookId(Long bookId);
}
