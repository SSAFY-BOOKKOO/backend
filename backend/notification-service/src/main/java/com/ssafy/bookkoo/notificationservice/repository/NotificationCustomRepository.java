package com.ssafy.bookkoo.notificationservice.repository;

import com.ssafy.bookkoo.notificationservice.entity.Notification;
import org.springframework.data.domain.Pageable;

import javax.management.NotificationFilter;
import java.util.List;

public interface NotificationCustomRepository {

    List<Notification> findByMemberIdAndCondition(Long memberId, Pageable pageable);
}