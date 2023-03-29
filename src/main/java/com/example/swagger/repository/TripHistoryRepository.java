package com.example.swagger.repository;

import com.example.swagger.entity.TripHistoryEntity;
import com.example.swagger.entity.TripHistoryItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripHistoryRepository extends JpaRepository<TripHistoryEntity,Long> {
    TripHistoryEntity findByCarEntityId(Long carId);
    boolean existsByCarEntityId(Long carId);
}
