package com.ttu.lunchbot.spring.services;

import com.ttu.lunchbot.spring.models.Cafe;
import com.ttu.lunchbot.spring.repositories.CafeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CafeService {

    private CafeRepository cafeRepository;

    public CafeService(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public Cafe addCafe(Cafe cafe) {
        return cafeRepository.save(cafe);
    }

    public Cafe getCafeById(long cafeId) {
        return cafeRepository.findOne(cafeId);
    }

    public List<Cafe> getAllCafes() {
        return cafeRepository.findAll();
    }
}
