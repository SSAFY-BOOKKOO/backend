package com.ssafy.bookkoo.bookservice.repository.review;

public interface ReviewLikeCustomRepository {

    /**
     * 멤버와 연관된 리뷰 라이크 삭제
     *
     * @param memberId 멤버 ID
     */
    void deleteByMemberId(Long memberId);
}
