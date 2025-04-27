package com.dev.oussama.history_service.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.oussama.history_service.model.HistoryLog;
import com.dev.oussama.history_service.repository.HistoryLogRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

  private final HistoryLogRepository repository;

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<HistoryLog>> getHistoryByUser(
        @PathVariable String userId) {
    return ResponseEntity.ok(repository.findByUserId(userId));
  }

  @GetMapping("/range")
  public ResponseEntity<List<HistoryLog>> getHistoryByDateRange(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end) {
    return ResponseEntity.ok(repository.findByTimestampBetween(start, end));
  }
}
