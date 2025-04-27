package com.dev.oussama.task_service.service;

import java.time.Instant;
import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.dev.oussama.shared_library.messaging.TaskEvent;
import com.dev.oussama.task_service.dto.TaskRequest;
import com.dev.oussama.task_service.dto.TaskResponse;
import com.dev.oussama.task_service.exception.TaskNotFoundException;
import com.dev.oussama.task_service.model.Task;
import com.dev.oussama.task_service.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {
  private final TaskRepository taskRepository;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public List<TaskResponse> getAllTasksByUserId(String userId) {
    return taskRepository.findAllByUserId(userId)
          .stream()
          .map(this::mapToResponse)
          .toList();
  }

  public TaskResponse createTask(TaskRequest request, String userId) {
    Task task = Task.builder()
        .title(request.getTitle())
        .description(request.getDescription())
        .status(request.getStatus())
        .userId(userId)
        .build();

    Task savedTask = taskRepository.save(task);
    sendKafkaEvent("TASK_CREATED", savedTask.getId(), userId);
    return mapToResponse(savedTask);
  }

  public TaskResponse updateTask(Long id, TaskRequest request, String userId) {
    Task task = taskRepository.findByIdAndUserId(id, userId)
        .orElseThrow(() -> new TaskNotFoundException("Task not found"));

    task.setTitle(request.getTitle());
    task.setDescription(request.getDescription());
    task.setStatus(request.getStatus());

    Task updatedTask = taskRepository.save(task);
    sendKafkaEvent("TASK_UPDATED", updatedTask.getId(), userId);
    return mapToResponse(updatedTask);
  }

  public void deleteTask(Long id, String userId) {
    Task task = taskRepository.findByIdAndUserId(id, userId)
        .orElseThrow(() -> new TaskNotFoundException("Task not found"));
    
    taskRepository.delete(task);
    sendKafkaEvent("TASK_DELETED", id, userId);
  }

  private void sendKafkaEvent(String eventType, Long taskId, String userId) {
    TaskEvent event = TaskEvent.builder()
            .eventType(eventType)
            .taskId(taskId)
            .userId(userId)
            .timestamp(Instant.now())
            .build();
    
    kafkaTemplate.send("task-events", event);
  }

  private TaskResponse mapToResponse(Task task) {
    return TaskResponse.builder()
          .id(task.getId())
          .title(task.getTitle())
          .description(task.getDescription())
          .status(task.getStatus())
          .userId(task.getUserId())
          .createdAt(task.getCreatedAt())
          .updatedAt(task.getUpdatedAt())
          .build();
  }
}
