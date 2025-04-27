package com.dev.oussama.history_service.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dev.oussama.history_service.model.HistoryLog;
import com.dev.oussama.history_service.repository.HistoryLogRepository;
import com.dev.oussama.shared_library.messaging.TaskEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KafkaEventConsumer {

  private final HistoryLogRepository repository;
  private static final Logger logger = LoggerFactory.getLogger(KafkaEventConsumer.class);

  @KafkaListener(topics = "task-events")
  public void handleTaskEvent(TaskEvent event) {
    try {
      HistoryLog log = HistoryLog.builder()
          .userId(event.getUserId())
          .action(event.getEventType())
          .taskId(event.getTaskId())
          .timestamp(event.getTimestamp())
          .build();

      repository.save(log);
      logger.info("History log saved: {}", log);
    } catch (Exception e) {
      logger.error("Error processing event: {}", event, e);
    }
  }
}
