package com.ssafy.bookkoo.bookservice.repository.review;

import com.ssafy.bookkoo.bookservice.entity.ReviewLike;
import com.ssafy.bookkoo.bookservice.entity.ReviewMemberId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, ReviewMemberId>,
    ReviewLikeCustomRepository {

    Optional<ReviewLike> findById(ReviewMemberId id);
}
