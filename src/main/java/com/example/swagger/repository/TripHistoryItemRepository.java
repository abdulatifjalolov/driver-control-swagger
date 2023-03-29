package com.example.swagger.repository;

import com.example.swagger.entity.TripHistoryItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripHistoryItemRepository extends JpaRepository<TripHistoryItemEntity,Long> {
}
