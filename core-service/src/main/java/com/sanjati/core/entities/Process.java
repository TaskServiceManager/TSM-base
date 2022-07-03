package com.sanjati.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "processes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Process {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "on_confirm")
    private Boolean onConfirm;
    @Column(name = "executor_id")
    private Long executorId;
    @OneToMany(mappedBy = "process")
    private List<TimePoint> timePoints;




    @Column(name = "executor_short_name")
    private String executorShortName;
    @Column(name = "executor_long_name")
    private String executorLongName;
    @Column(name = "task")
    private String task;


    @Column(name = "status")
    private String status;

    @CreationTimestamp
    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;


    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;
    @Column(name = "finished_at")
    private LocalDateTime finishedAt;
    @Column(name = "in_process_total")
    private LocalDateTime inProcessTotal;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
