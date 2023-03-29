package com.example.swagger.service;

import com.example.swagger.common.exception.RecordNotFountException;
import com.example.swagger.entity.CarEntity;
import com.example.swagger.entity.TripHistoryEntity;
import com.example.swagger.entity.TripHistoryItemEntity;
import com.example.swagger.entity.model.dto.request.TripHistoryDTO;
import com.example.swagger.repository.CarRepository;
import com.example.swagger.repository.TripHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripHistoryService {
    private final TripHistoryRepository tripHistoryRepository;
    private final CarRepository carRepository;

    public void create(TripHistoryDTO tripHistoryDTO) {
        Long carId = tripHistoryDTO.getCarId();
        Optional<CarEntity> optionalCarEntity =
                carRepository.findById(carId);

        if (optionalCarEntity.isEmpty()) {
            throw new RecordNotFountException(MessageFormat.format("car not found for {0}", carId));
        }

        boolean existsByCarEntityId =
                tripHistoryRepository.existsByCarEntityId(tripHistoryDTO.getCarId());

        if (existsByCarEntityId) {
            TripHistoryEntity tripHistoryEntity = tripHistoryRepository.findByCarEntityId(carId);
            tripHistoryEntity.getTripHistoryItemEntities().addAll(tripHistoryDTO.getTripHistoryItemEntities());
            tripHistoryRepository.save(tripHistoryEntity);
            return;
        }

        TripHistoryEntity savedTripHistoryEntity =
                convertToTripHistoryEntity(optionalCarEntity.get(), tripHistoryDTO);
        tripHistoryRepository.save(savedTripHistoryEntity);
    }

    private TripHistoryEntity convertToTripHistoryEntity(CarEntity carEntity, TripHistoryDTO tripHistoryDTO) {
        return TripHistoryEntity.builder()
                .tripHistoryItemEntities(tripHistoryDTO.getTripHistoryItemEntities())
                .carEntity(carEntity)
                .build();
    }
}
