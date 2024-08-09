package com.ssafy.bookkoo.memberservice.repository.custom;

import com.ssafy.bookkoo.memberservice.entity.Quote;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface QuoteCustomRepository {

    List<Quote> findByMemberId(Long memberId, Pageable pageable);

}
