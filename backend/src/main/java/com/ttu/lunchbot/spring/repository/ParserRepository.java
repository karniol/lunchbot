package com.ttu.lunchbot.spring.repository;

import com.ttu.lunchbot.spring.model.Parser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParserRepository extends JpaRepository<Parser, Long> {

    List<Parser> findAll();

}
