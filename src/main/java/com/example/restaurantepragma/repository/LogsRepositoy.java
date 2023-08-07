package com.example.restaurantepragma.repository;

import com.example.restaurantepragma.entities.Customer;
import com.example.restaurantepragma.entities.Logs;
import com.example.restaurantepragma.entities.Order;
import com.example.restaurantepragma.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogsRepositoy extends JpaRepository<Logs,Long> {
    Logs findByOrderLogIdAndStatus(Order order, OrderStatus status);

    Logs findByOrderLogIdAndId(Order order, Long id);

}
