package com.sanjati.core.repositories;


import com.sanjati.core.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    @Query("select o from Task o where o.ownerName = ?1")
    List<Task> findAllByUsername(String username);

    @Query("select t from Task t join t.executors as e where e.id = ?1")
    Page<Task> getAllAssignedTasksByExecutorId(Long executorId, Pageable pageable);
}
