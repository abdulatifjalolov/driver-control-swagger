package com.example.swagger.service;

import com.example.swagger.common.exception.RecordNotFountException;
import com.example.swagger.entity.CarEntity;
import com.example.swagger.entity.TripHistoryItemEntity;
import com.example.swagger.entity.model.dto.TripHistoryItemDTO;
import com.example.swagger.repository.CarRepository;
import com.example.swagger.repository.TripHistoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripHistoryService {
    private final TripHistoryItemRepository tripHistoryItemRepository;
    private final CarRepository carRepository;

    public void create(TripHistoryItemDTO tripHistoryItemDTO) {
        Long carId = tripHistoryItemDTO.getCarId();
        Optional<CarEntity> optionalCarEntity = carRepository.findById(carId);
        if (optionalCarEntity.isEmpty()) {
            throw new RecordNotFountException(MessageFormat.format("car not found for {0}", carId));
        }
        TripHistoryItemEntity tripHistoryItemEntity = TripHistoryItemEntity.of(tripHistoryItemDTO);
        tripHistoryItemEntity.setCarEntity(optionalCarEntity.get());
        tripHistoryItemRepository.save(tripHistoryItemEntity);
    }
}
