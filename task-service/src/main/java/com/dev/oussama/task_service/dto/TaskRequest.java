package com.dev.oussama.task_service.dto;

import com.dev.oussama.task_service.model.TaskStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
  @NotBlank(message = "Title is required")
  private String title;

  private String description;

  @NotNull(message = "Status is required")
  private TaskStatus status;
}