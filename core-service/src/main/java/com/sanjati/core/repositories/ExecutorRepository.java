package com.sanjati.core.repositories;

import com.sanjati.core.entities.Executor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutorRepository extends JpaRepository<Executor, Long>, JpaSpecificationExecutor<Executor> {
}
