package com.ttu.lunchbot.spring.repository;

import com.ttu.lunchbot.spring.model.OpeningTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpeningTimeRepository extends JpaRepository<OpeningTime, Long> {

    List<OpeningTime> findAll();

}
