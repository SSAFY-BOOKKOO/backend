package com.ssafy.bookkoo.bookservice.repository;

import com.ssafy.bookkoo.bookservice.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByBookId(Long bookId);
}
