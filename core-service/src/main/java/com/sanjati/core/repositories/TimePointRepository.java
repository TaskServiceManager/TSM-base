package com.sanjati.core.repositories;


import com.sanjati.core.entities.TimePoint;
import com.sanjati.core.enums.TimePointStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TimePointRepository extends JpaRepository<TimePoint, Long>, JpaSpecificationExecutor<TimePoint> {
   @Query("select count (tp) from TimePoint tp where tp.executorId = :executorId and tp.status = :status" )
    public Integer checkByExecutorIdAndStatus(Long  executorId, TimePointStatus status);
   @Query("select count (tp) from TimePoint tp where tp.task.id = :taskId")
    public Integer checkCountTimePoints(Long taskId);
}
