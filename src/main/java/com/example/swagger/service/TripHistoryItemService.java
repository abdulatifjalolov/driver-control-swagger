package com.example.swagger.service;

import com.example.swagger.entity.TripHistoryItemEntity;
import com.example.swagger.entity.model.dto.request.TripHistoryItemDTO;
import com.example.swagger.repository.TripHistoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripHistoryItemService {
    private final TripHistoryItemRepository tripHistoryItemRepository;

    public void create(TripHistoryItemDTO tripHistoryItemDTO) {
        TripHistoryItemEntity tripHistoryItemEntity =
                TripHistoryItemEntity.of(tripHistoryItemDTO);
        tripHistoryItemRepository.save(tripHistoryItemEntity);
    }

    public TripHistoryItemEntity get(Long id) {
        return tripHistoryItemRepository.findById(id).orElse(null);
    }
}
