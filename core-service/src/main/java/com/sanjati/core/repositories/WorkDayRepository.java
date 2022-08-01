package com.sanjati.core.repositories;

import com.sanjati.core.entities.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {

    @Query("select d from WorkDay d where d.executorId = ?1 and" +
            " d.start = (select max(d.start) from WorkDay d where d.executorId = ?1)")
    Optional<WorkDay> findLastWorkDayByExecutorId(Long executorId);

    @Query("select new WorkDay(d.executorId, max (d.start), max(d.end)) from WorkDay d group by d.executorId")
    List<WorkDay> findAllLastWorkDays();
}
