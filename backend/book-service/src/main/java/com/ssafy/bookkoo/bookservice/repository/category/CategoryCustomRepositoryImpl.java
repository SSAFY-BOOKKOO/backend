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

@Repository
@AllArgsConstructor
public class CategoryCustomRepositoryImpl implements CategoryCustomRepository {

    private final JPAQueryFactory queryFactory;

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
