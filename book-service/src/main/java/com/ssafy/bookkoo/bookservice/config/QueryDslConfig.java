package com.ssafy.bookkoo.bookservice.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QueryDSL 사용을 위한 Factory를 Bean으로 등록 : JPAQueryFactory를 QueryDSL을 이용한 JPA 쿼리를 빌드하는 Factory 역할로서 사용할
 * 수 있도록 Bean으로 등록
 */
@Configuration
public class QueryDslConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
