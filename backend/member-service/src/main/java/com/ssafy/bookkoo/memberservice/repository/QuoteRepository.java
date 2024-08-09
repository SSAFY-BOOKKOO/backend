package com.ssafy.bookkoo.memberservice.repository;

import com.ssafy.bookkoo.memberservice.entity.Quote;
import com.ssafy.bookkoo.memberservice.repository.custom.QuoteCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long>, QuoteCustomRepository {

}
