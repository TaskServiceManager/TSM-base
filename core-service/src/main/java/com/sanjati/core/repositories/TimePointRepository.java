package com.sanjati.core.repositories;


import com.sanjati.core.entities.TimePoint;
import com.sanjati.core.enums.TaskStatus;
import com.sanjati.core.enums.TimePointStatus;
import com.sanjati.core.services.TimePointService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TimePointRepository extends JpaRepository<TimePoint, Long>, JpaSpecificationExecutor<TimePoint> {
   @Query("select count (tp) > 0 from TimePoint tp where tp.executorId = :executorId and tp.status = :status" )
    public boolean isCountMoreThanZeroByExecutorIdAndStatus(Long  executorId, TimePointStatus status);
//   @Query("select count (tp) > 0 from TimePoint tp where tp.task.id = :taskId")
//    public boolean isCountMoreThanZeroByTaskId(Long taskId);

    List<TimePoint> findAllByStatus(TimePointStatus status);
}
