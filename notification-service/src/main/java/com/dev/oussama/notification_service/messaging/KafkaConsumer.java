package com.dev.oussama.notification_service.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dev.oussama.notification_service.service.NotificationService;
import com.dev.oussama.shared_library.messaging.TaskEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

  private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
  private final NotificationService notificationService;

  @KafkaListener(topics = "task-events")
  public void handleTaskEvent(TaskEvent event) {
    logger.info("Received TaskEvent: {}", event);

    String message = switch (event.getEventType()) {
      case "TASK_CREATED" -> String.format("Nouvelle tâche créée (#%d)", event.getTaskId());
      case "TASK_UPDATED" -> String.format("Tâche modifiée (#%d)", event.getTaskId());
      case "TASK_DELETED" -> String.format("Tâche supprimée (#%d)", event.getTaskId());
      default -> "Événement inconnu";
    };
    
    notificationService.sendNotification(event.getUserId(), message);
  }
}