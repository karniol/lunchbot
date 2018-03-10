package com.ttu.lunchbot.spring.repository;

import com.ttu.lunchbot.spring.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByCafe_Id(long cafeId);

    List<Menu> findAll();

}
