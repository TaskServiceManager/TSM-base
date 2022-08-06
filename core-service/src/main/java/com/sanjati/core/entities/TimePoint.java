package com.sanjati.core.entities;

import com.sanjati.core.enums.TimePointStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Duration;
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
    @Enumerated(value = EnumType.STRING)
    private TimePointStatus status;

    @CreationTimestamp
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    public String getDuration() {
        if(startedAt==null || finishedAt==null) {
            return null;
        }
        Duration duration = Duration.between(startedAt, finishedAt);
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
    }

}
