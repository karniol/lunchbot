package com.ttu.lunchbot.spring.repositories;

import com.ttu.lunchbot.spring.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends CrudRepository<Menu, Long>{

    List<Menu> findAll();
}