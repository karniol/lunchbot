package com.ttu.lunchbot.spring.repositories;

import com.ttu.lunchbot.spring.models.MenuItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends CrudRepository<MenuItem, Long>{

    List<MenuItem> findAll();
}