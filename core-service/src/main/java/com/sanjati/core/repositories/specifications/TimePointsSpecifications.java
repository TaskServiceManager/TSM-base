package com.sanjati.core.repositories.specifications;


import com.sanjati.core.entities.TimePoint;
import com.sanjati.core.enums.TimePointStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TimePointsSpecifications {
    public static Specification<TimePoint> timeGreaterOrEqualsThan(LocalDateTime time) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), time);
        };
    }

    public static Specification<TimePoint> timeLessOrEqualsThan(LocalDateTime time) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), time);
        };
    }
    public static Specification<TimePoint> executorIdEquals(Long executorId){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("executorId"), executorId);
    }
    public static Specification<TimePoint> taskIdEquals(Long taskId){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("taskId"), taskId);
    }
    public static Specification<TimePoint> statusEquals(TimePointStatus status){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }
}
