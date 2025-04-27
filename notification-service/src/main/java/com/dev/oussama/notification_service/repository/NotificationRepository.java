package com.dev.oussama.notification_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.oussama.notification_service.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
