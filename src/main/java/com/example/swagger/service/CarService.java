package com.example.swagger.service;

import com.example.swagger.common.exception.RecordNotFountException;
import com.example.swagger.common.exception.StateNumberAlreadyExistException;
import com.example.swagger.entity.CarEntity;
import com.example.swagger.entity.TripHistoryItemEntity;
import com.example.swagger.entity.model.dto.CarRequestDTO;
import com.example.swagger.entity.model.dto.TripHistoryItemDTO;
import com.example.swagger.entity.model.dto.CarResponseDTO;
import com.example.swagger.repository.CarRepository;
import com.example.swagger.repository.TripHistoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final TripHistoryItemRepository tripHistoryItemRepository;

    public void create(CarRequestDTO carRequestDTO) {
        if (carRepository.existsByStateNumber(carRequestDTO.getStateNumber())) {
            throw new StateNumberAlreadyExistException(MessageFormat.format("state number already exist for {0}", carRequestDTO.getStateNumber()));
        }
        carRepository.save(CarEntity.of(carRequestDTO));
    }

    public CarResponseDTO get(Long id) {
        Optional<CarEntity> optionalCarEntity = carRepository.findById(id);
        if (optionalCarEntity.isEmpty()) {
            throw new RecordNotFountException(MessageFormat.format("car not found for {0}", id));
        }
        CarEntity carEntity = optionalCarEntity.get();
        //check
        return convertToCarResponseDTO(carEntity);
    }

    public CarResponseDTO update(Long id, CarRequestDTO carRequestDTO) {
        Optional<CarEntity> optionalCarEntity = carRepository.findById(id);
        if (optionalCarEntity.isEmpty()) {
            throw new RecordNotFountException(MessageFormat.format("car not found for {0}", id));
        }
        CarEntity oldCarEntity = optionalCarEntity.get();
        CarEntity carEntity = CarEntity.of(carRequestDTO);
        carEntity.setId(id);
        carEntity.setCreatedBy(oldCarEntity.getCreatedBy());
        carEntity.setCreatedAt(oldCarEntity.getCreatedAt());
        CarEntity savedCarEntity = carRepository.save(carEntity);
        return convertToCarResponseDTO(savedCarEntity);
    }

    public void delete(Long carId) {
        if (carRepository.existsById(carId)) {
            carRepository.deleteById(carId);
        }
    }

    private CarResponseDTO convertToCarResponseDTO(CarEntity carEntity) {
        List<TripHistoryItemDTO> tripHistoryItemDTOList = null;
        if (carEntity.getTripHistoryItemEntities() != null) {
            tripHistoryItemDTOList = convertToTripHistoryItemDTOList(carEntity.getTripHistoryItemEntities());
        }
        return new CarResponseDTO(
                carEntity.getName(),
                carEntity.getModel(),
                carEntity.getStateNumber(),
                carEntity.getModelYear(),
                carEntity.getMileage(),
                carEntity.getCreatedBy(),
                carEntity.getLastModifiedBy(),
                carEntity.getCreatedAt(),
                carEntity.getLastModifiedAt(),
                tripHistoryItemDTOList
                );
    }

    private List<TripHistoryItemDTO> convertToTripHistoryItemDTOList(List<TripHistoryItemEntity> tripHistoryItemEntities) {
        return tripHistoryItemEntities
                .stream()
                .map(
                        (item) ->
                                new TripHistoryItemDTO(
                                        item.getId(),
                                        item.getDepartureDate(),
                                        item.getDepartureAddress(),
                                        item.getArrivalDate(),
                                        item.getArrivalAddress()
                                )
                ).toList();
    }
}
