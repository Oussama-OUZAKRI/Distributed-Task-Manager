package com.dev.oussama.task_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.oussama.task_service.dto.TaskRequest;
import com.dev.oussama.task_service.dto.TaskResponse;
import com.dev.oussama.task_service.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
  private final TaskService taskService;

  @GetMapping
  public ResponseEntity<List<TaskResponse>> getAllTasks(
      @RequestHeader("X-User-ID") String userId) {
    return ResponseEntity.ok(taskService.getAllTasksByUserId(userId));
  }

  @PostMapping
  public ResponseEntity<TaskResponse> createTask(
      @RequestBody @Valid TaskRequest request,
      @RequestHeader("X-User-ID") String userId
    ) {
    return new ResponseEntity<>(
      taskService.createTask(request, userId), 
      HttpStatus.CREATED
    );
  }

  @PutMapping("/{id}")
  public ResponseEntity<TaskResponse> updateTask(
          @PathVariable Long id,
          @RequestBody @Valid TaskRequest request,
          @RequestHeader("X-User-ID") String userId) {
      return ResponseEntity.ok(taskService.updateTask(id, request, userId));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTask(
          @PathVariable Long id,
          @RequestHeader("X-User-ID") String userId) {
      taskService.deleteTask(id, userId);
      return ResponseEntity.noContent().build();
  }
}
