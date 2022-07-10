package com.sanjati.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "executors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Executor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

//    @ManyToMany //из-за этого стек оверфлоу может быть
//    @JoinTable(name = "tasks_executors",
//            joinColumns = @JoinColumn(name = "executor_id"),
//            inverseJoinColumns = @JoinColumn(name = "task_id"))
//    private Collection<Task> tasks;
}
