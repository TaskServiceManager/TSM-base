package com.sanjati.core.repositories;


import com.sanjati.core.entities.Task;
import com.sanjati.core.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    @Query("select  count (t) > 0  from Task t where t.id = :taskId and t.ownerId = :id" )
    public boolean isCountMoreThanZeroByOwnerIdAndTaskId(Long taskId, Long id);
    @Query("select t.status from Task t where t.id = :taskId")
    public TaskStatus findStatusByTaskId(Long taskId);

}
