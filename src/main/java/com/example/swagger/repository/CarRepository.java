package com.example.swagger.repository;

import com.example.swagger.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<CarEntity,Long> {
    boolean existsByStateNumber(String stateNumber);
}
