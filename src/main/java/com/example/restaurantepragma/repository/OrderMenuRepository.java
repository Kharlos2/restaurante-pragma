package com.example.restaurantepragma.repository;

import com.example.restaurantepragma.entities.Order;
import com.example.restaurantepragma.entities.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenu,Long> {
    List<OrderMenu> findByOrderId(Order order);
}
