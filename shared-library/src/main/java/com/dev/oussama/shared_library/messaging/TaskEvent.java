package com.dev.oussama.shared_library.messaging;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskEvent {
  private String eventType;
  private Long taskId;
  private String userId;
  private Instant timestamp;
}
