package com.sanjati.core.entities;

import com.sanjati.core.enums.TimePointStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "time_points")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "task_id")
    private Long taskId;
    @Column(name = "executor_id")
    private Long executorId;
    @Column(name = "status")
    private TimePointStatus status;

    @CreationTimestamp
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

}
