package com.ttu.lunchbot.spring.repository;

import com.ttu.lunchbot.spring.model.FoodService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodServiceRepository extends JpaRepository<FoodService, Long> {

    List<FoodService> findAll();
}
