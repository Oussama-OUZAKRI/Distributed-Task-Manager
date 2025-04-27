package com.dev.oussama.notification_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.Getter;

@Getter
@Configuration
@EnableScheduling
public class NotificationConfig {
  @Value("${notification.sender.email}")
  private String senderEmail;
}
