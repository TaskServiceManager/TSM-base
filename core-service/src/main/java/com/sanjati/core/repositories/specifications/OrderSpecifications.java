package com.sanjati.core.repositories.specifications;


import com.sanjati.core.entities.Order;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OrderSpecifications {
    public static Specification<Order> timeGreaterOrEqualsThan(LocalDateTime time) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), time);
        };
    }

    public static Specification<Order> timeLessThanOrEqualsThan(LocalDateTime time) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), time);
        };
    }
    public static Specification<Order> idEquals(Long id){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("userId"), id);
    }
    public static Specification<Order> usernameEquals(String username){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("userNick"), username);
    }

}
