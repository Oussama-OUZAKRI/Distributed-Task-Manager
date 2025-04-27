package com.dev.oussama.history_service.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.oussama.history_service.model.HistoryLog;

public interface HistoryLogRepository extends JpaRepository<HistoryLog, Long> {
  List<HistoryLog> findByUserId(String userId);
  List<HistoryLog> findByTimestampBetween(Instant start, Instant end);
}
