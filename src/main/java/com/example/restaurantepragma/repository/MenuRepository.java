package com.example.restaurantepragma.repository;

import com.example.restaurantepragma.entities.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {
    boolean existsByNameMenu(String name);

    Page<Menu> findByCategoryAndCampus(String category, String campus, Pageable pageable);
}

