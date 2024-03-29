package com.sanjati.core.repositories;


import com.sanjati.core.entities.TimePoint;
import com.sanjati.core.enums.TimePointStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TimePointRepository extends JpaRepository<TimePoint, Long>, JpaSpecificationExecutor<TimePoint> {

    boolean existsByExecutorIdAndStatus(Long executorId, TimePointStatus status);
    Optional<TimePoint> findFirstByExecutorIdAndStatus(Long executorId, TimePointStatus status);
    Optional<TimePoint> findByTaskIdAndExecutorIdAndStatus(Long taskId, Long executorId, TimePointStatus status);
    List<TimePoint> findByTaskIdAndStatus(Long taskId, TimePointStatus status);
    List<TimePoint> findAllByStatus(TimePointStatus status);
}
