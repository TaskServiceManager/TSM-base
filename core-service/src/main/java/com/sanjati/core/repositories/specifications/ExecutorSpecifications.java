package com.sanjati.core.repositories.specifications;

import com.sanjati.core.entities.Executor;
import com.sanjati.core.entities.Task;
import org.springframework.data.jpa.domain.Specification;

public class ExecutorSpecifications {
    public static Specification<Executor> idEquals(Long id){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("userId"), id);
    }
}
