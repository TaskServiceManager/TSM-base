package com.sanjati.core.repositories;


import com.sanjati.core.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    @Query("select o from Order o where o.userNick = ?1")
    List<Order> findAllByUsername(String username);
}
