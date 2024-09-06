package com.ssafy.bookkoo.notificationservice.repository;

import com.ssafy.bookkoo.notificationservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long>,
    NotificationCustomRepository {

}
