package com.sanjati.auth.repositories.specifications;

import com.sanjati.auth.entities.Role;
import com.sanjati.auth.entities.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<User> usernameLike(String usernamePart){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("username"), String.format("%%%s%%", usernamePart));
    }
    public static Specification<User> userIdEquals(Long id){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
    public static Specification<User> userRoleContainsIn(Role role){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isMember(role,root.get("roles"));
    }
}
