package com.sanjati.core.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "work_days")
@NoArgsConstructor
public class WorkDay {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "executor_id")
    private Long executorId;

    @Column(name = "start")
    @CreationTimestamp
    private LocalDateTime start;

    @Column(name = "end")
    private LocalDateTime end;

    public WorkDay(LocalDateTime end) {
        this.end = end;
    }

    public WorkDay(Long executorId, LocalDateTime start, LocalDateTime end) {
        this.executorId = executorId;
        this.start = start;
        this.end = end;
    }
}
