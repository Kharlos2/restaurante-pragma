package com.example.restaurantepragma.repository;

import com.example.restaurantepragma.entities.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenu,Long> {
}
