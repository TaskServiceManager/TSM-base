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
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "is_active")
    private Boolean isActive;
    @OneToMany(mappedBy = "order")
    private List<Process> processes;
    @OneToMany(mappedBy = "order",fetch = EAGER)
    private List<Commit> commits;



    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;


    @Column(name = "user_nick")
    private String userNick;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "user_short_name")
    private String userShortName;
    @Column(name = "user_long_name")
    private String userLongName;

    @Column(name = "status")
    private String status;

    @Column(name = "executors")
    private String executors;




    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
