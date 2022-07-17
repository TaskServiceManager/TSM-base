package com.sanjati.core.repositories;


import com.sanjati.core.entities.TimePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface TimePointRepository extends JpaRepository<TimePoint, Long>, JpaSpecificationExecutor<TimePoint> {
}
