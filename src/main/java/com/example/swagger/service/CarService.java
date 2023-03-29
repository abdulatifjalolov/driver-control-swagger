package com.example.swagger.service;

import com.example.swagger.common.exception.RecordNotFountException;
import com.example.swagger.common.exception.StateNumberAlreadyExistException;
import com.example.swagger.entity.CarEntity;
import com.example.swagger.entity.TripHistoryEntity;
import com.example.swagger.entity.TripHistoryItemEntity;
import com.example.swagger.entity.model.dto.response.CarResponseDTO;
import com.example.swagger.entity.model.dto.request.CarRequestDTO;
import com.example.swagger.repository.CarRepository;
import com.example.swagger.repository.TripHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final TripHistoryRepository tripHistoryRepository;

    public void create(CarRequestDTO carRequestDTO){
        if (carRepository.existsByStateNumber(carRequestDTO.getStateNumber())) {
            throw new StateNumberAlreadyExistException(MessageFormat.format("state number already exist for {0}", carRequestDTO.getStateNumber()));
        }
        carRepository.save(CarEntity.of(carRequestDTO));
    }

    public CarResponseDTO get(Long id) {
        Optional<CarEntity> optionalCarEntity = carRepository.findById(id);
        if (optionalCarEntity.isEmpty()) {
            throw new RecordNotFountException(MessageFormat.format("car not found for {0}",id));
        }
        CarEntity carEntity = optionalCarEntity.get();
        return convertToCarResponseDTO(carEntity);
    }

    public CarEntity update(Long id, CarRequestDTO carRequestDTO) {
        Optional<CarEntity> optionalCarEntity = carRepository.findById(id);
        if (optionalCarEntity.isEmpty()) {
            throw new RecordNotFountException(MessageFormat.format("car not found for {0}",id));
        }
        CarEntity oldCarEntity = optionalCarEntity.get();
        CarEntity carEntity = CarEntity.of(carRequestDTO);
        carEntity.setId(id);
        carEntity.setCreatedBy(oldCarEntity.getCreatedBy());
        carEntity.setCreatedAt(oldCarEntity.getCreatedAt());
        return carRepository.save(carEntity);
    }

    public void delete(Long carId) {
        if (carRepository.existsById(carId)) {
            carRepository.deleteById(carId);
        }
    }

    private CarResponseDTO convertToCarResponseDTO(CarEntity carEntity) {
        TripHistoryEntity byCarEntityId =
                tripHistoryRepository.findByCarEntityId(carEntity.getId());

        List<TripHistoryItemEntity> tripHistoryItemEntities = null;

        if (byCarEntityId != null) {
             tripHistoryItemEntities = byCarEntityId.getTripHistoryItemEntities();
        }
        return new CarResponseDTO(
                carEntity.getName(),
                carEntity.getModel(),
                carEntity.getStateNumber(),
                carEntity.getModelYear(),
                carEntity.getMileage(),
                carEntity.getCreatedBy(),
                carEntity.getLastModifiedBy()
                ,carEntity.getCreatedAt(),
                carEntity.getLastModifiedAt(),
                tripHistoryItemEntities
        );
    }
}
