package com.dev.oussama.task_service.dto;

import java.time.LocalDateTime;

import com.dev.oussama.task_service.model.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponse {
  private Long id;
  private String title;
  private String description;
  private TaskStatus status;
  private String userId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
