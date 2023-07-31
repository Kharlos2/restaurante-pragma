package com.example.restaurantepragma.repository;

import com.example.restaurantepragma.entities.Order;
import com.example.restaurantepragma.enums.OrderStatus;
import org.aspectj.weaver.ast.And;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Page<Order> findByStateRequestedAndFranchise(OrderStatus stateRequested, String franchise, Pageable pageable);
}
