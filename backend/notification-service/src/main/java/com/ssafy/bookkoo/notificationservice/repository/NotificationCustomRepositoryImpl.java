package com.ssafy.bookkoo.notificationservice.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bookkoo.notificationservice.entity.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.management.NotificationFilter;
import java.util.List;

import static com.ssafy.bookkoo.notificationservice.entity.QNotification.notification;

@Slf4j
@Repository
@RequiredArgsConstructor
public class NotificationCustomRepositoryImpl implements NotificationCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Notification> findByMemberIdAndCondition(Long memberId, Pageable pageable) {
        log.info("pageSize = {}", pageable.getPageSize());
        log.info("offset = {}", pageable.getOffset());

        return queryFactory.selectFrom(notification)
                           .where(notification.memberId.eq(memberId))
                           .offset(pageable.getOffset())
                           .limit(pageable.getPageSize())
                           .orderBy(notification.createdAt.desc())
                           .fetch();
    }
}
