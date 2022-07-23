package com.sanjati.core.repositories;


import com.sanjati.core.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    @Query("select  count (t)  from Task t where t.id = :taskId and t.ownerId = :id" )
    public Integer checkCountByOwnerIdAndTaskId(Long taskId, Long id);

}
