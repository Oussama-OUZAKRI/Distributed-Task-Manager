package com.dev.oussama.task_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.oussama.task_service.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
  List<Task> findAllByUserId(String userId);
  Optional<Task> findByIdAndUserId(Long id, String userId);
}
