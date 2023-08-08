package com.example.restaurantepragma.repository;

import com.example.restaurantepragma.entities.Logs;
import com.example.restaurantepragma.entities.Order;
import com.example.restaurantepragma.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogsRepository extends JpaRepository<Logs,Long> {
    Logs findByOrderLogIdAndStatus(Order order, OrderStatus status);

    List<Logs> findByOrderLogId(Order order);

}
