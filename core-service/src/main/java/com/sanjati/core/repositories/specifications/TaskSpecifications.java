package com.sanjati.core.repositories.specifications;



import com.sanjati.core.entities.Task;
import com.sanjati.core.enums.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TaskSpecifications {
    public static Specification<Task> timeGreaterOrEqualsThan(LocalDateTime time) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), time);
        };
    }

    public static Specification<Task> timeLessThanOrEqualsThan(LocalDateTime time) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), time);
        };
    }
    public static Specification<Task> ownerIdEquals(Long id){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("ownerId"), id);
    }
    public static Specification<Task> usernameEquals(String username){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("ownerName"), username);
    }
    public static Specification<Task> statusEquals(TaskStatus status){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }
    public static Specification<Task> executorIdContainsIn(Long executorId){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isMember(executorId,root.get("executors"));
    }


}
