package com.ssafy.bookkoo.bookservice.repository.category;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bookkoo.bookservice.dto.category.CategorySearchParam;
import com.ssafy.bookkoo.bookservice.entity.Category;
import com.ssafy.bookkoo.bookservice.entity.QCategory;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * CategoryCustomRepository의 구현체로, 특정 필터 조건을 기반으로 카테고리를 검색하는 로직을 처리합니다.
 */
@Repository
@AllArgsConstructor
public class CategoryCustomRepositoryImpl implements CategoryCustomRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 주어진 검색 파라미터를 기반으로 카테고리를 검색합니다.
     *
     * @param searchParam 카테고리 검색 파라미터
     * @return 검색된 카테고리 리스트
     */
    @Override
    public List<Category> findByFilter(CategorySearchParam searchParam) {
        QCategory category = QCategory.category;
        BooleanBuilder predicate = new BooleanBuilder();
        PathBuilder<Category> entityPath = new PathBuilder<>(Category.class, "category");

        if (searchParam != null && searchParam.field() != null && !searchParam.values()
                                                                              .isEmpty()) {
            BooleanExpression inExpression = entityPath.getString(searchParam.field())
                                                       .in(searchParam.values());
            predicate.and(inExpression);
        }

        return queryFactory.selectFrom(category)
                           .where(predicate)
                           .fetch();
    }
}
