package com.sanjati.core.repositories;

import com.sanjati.core.entities.Order;
import com.sanjati.core.entities.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Long> {

    @Query("select p.order from Process p where p.executorId = ?1")
    List<Order> findAllExecutorsOrdersById(Long id);
}
