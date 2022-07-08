package com.sanjati.core.repositories.specifications;


import com.sanjati.core.entities.Task;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OrderSpecifications {
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
    public static Specification<Task> idEquals(Long id){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("userId"), id);
    }
    public static Specification<Task> usernameEquals(String username){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("userNick"), username);
    }

}
