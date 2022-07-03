package com.sanjati.core.repositories.specifications;


import com.sanjati.core.entities.Order;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecifications {
    public static Specification<Order> timeGreaterOrEqualsThan(Long time) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("created_at"), time);
    }

    public static Specification<Order> timeLessThanOrEqualsThan(Long time) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("created_at"), time);
    }
    public static Specification<Order> idEquals(Long id){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("user_id"), id);
    }

}
