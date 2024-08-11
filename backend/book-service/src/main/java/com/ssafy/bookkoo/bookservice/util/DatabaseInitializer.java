package com.ssafy.bookkoo.bookservice.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DatabaseInitializer implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // pg_trgm 확장을 설치. 이 확장은 PostgreSQL에서 텍스트 검색 및 문자열 유사성 비교 기능을 제공
        String createExtensionSql = "CREATE EXTENSION IF NOT EXISTS pg_trgm";

        // 책 제목(title)에 대한 GIN 인덱스를 생성. 이 인덱스는 pg_trgm 확장을 이용해 LIKE 또는 ILIKE 검색을 최적화
        String createTitleIndexSql = "CREATE INDEX IF NOT EXISTS book_title_trgm_idx ON book USING GIN (title gin_trgm_ops)";

        // 책 저자(author)에 대한 GIN 인덱스를 생성. 이 인덱스는 저자 이름에 대한 LIKE 또는 ILIKE 검색을 최적화
        String createAuthorIndexSql = "CREATE INDEX IF NOT EXISTS book_author_trgm_idx ON book USING GIN (author gin_trgm_ops)";

        // 책 출판사(publisher)에 대한 GIN 인덱스를 생성. 이 인덱스는 출판사 이름에 대한 LIKE 또는 ILIKE 검색을 최적화
        String createPublisherIndexSql = "CREATE INDEX IF NOT EXISTS book_publisher_trgm_idx ON book USING GIN (publisher gin_trgm_ops)";

        entityManager.createNativeQuery(createExtensionSql)
                     .executeUpdate();
        entityManager.createNativeQuery(createTitleIndexSql)
                     .executeUpdate();
        entityManager.createNativeQuery(createAuthorIndexSql)
                     .executeUpdate();
        entityManager.createNativeQuery(createPublisherIndexSql)
                     .executeUpdate();
    }
}
