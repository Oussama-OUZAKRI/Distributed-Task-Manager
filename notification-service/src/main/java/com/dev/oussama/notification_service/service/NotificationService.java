package com.dev.oussama.notification_service.service;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.dev.oussama.notification_service.config.NotificationConfig;
import com.dev.oussama.notification_service.model.Notification;
import com.dev.oussama.notification_service.repository.NotificationRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
  
  private final SimpMessagingTemplate websocketTemplate;
  private final JavaMailSender mailSender;
  private final NotificationConfig config;
  private final NotificationRepository notificationRepository;

  private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

  @Async
  public void sendNotification(String userId, String message) {
    boolean websocketDelivered = false;
    boolean emailDelivered = false;
    
    try {
      websocketTemplate.convertAndSendToUser(userId, "/queue/notifications", message);
      websocketDelivered = true;
    } catch (Exception e) {
      logger.error("Erreur WebSocket pour {}", userId, e);
    }
    
    try {
      sendEmailNotification(userId, message);
      emailDelivered = true;
    } catch (MessagingException e) {
      logger.error("Erreur email pour {}", userId, e);
    }
    
    Notification notification = Notification.builder()
        .userId(userId)
        .message(message)
        .timestamp(Instant.now())
        .delivered(websocketDelivered || emailDelivered)
        .build();
    
    notificationRepository.save(notification);
  }

  private void sendEmailNotification(String userId, String message) {
    try {
      MimeMessage mail = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mail);
      helper.setFrom(config.getSenderEmail());
      helper.setTo(userId);
      helper.setSubject("Notification de tâche");
      helper.setText(message);
      mailSender.send(mail);
    } catch (MessagingException e) {
      logger.error("Erreur d'envoi d'email à {}", userId, e);
    }
  }
}