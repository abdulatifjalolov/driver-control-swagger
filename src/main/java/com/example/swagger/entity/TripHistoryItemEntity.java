package com.example.swagger.entity;

import com.example.swagger.entity.model.dto.request.TripHistoryItemDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "trip_history_item")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TripHistoryItemEntity extends BaseEntity {
    @Column(nullable = false)
    private Timestamp departureDate;
    @Column(nullable = false)
    private String departureAddress;
    @Column(nullable = false)
    private Timestamp arrivalDate;
    @Column(nullable = false)
    private String arrivalAddress;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private TripHistoryEntity tripHistoryEntity;
    public static TripHistoryItemEntity of(TripHistoryItemDTO tripHistoryItemDTO) {
        return TripHistoryItemEntity.builder()
                .arrivalAddress(tripHistoryItemDTO.getArrivalAddress())
                .arrivalDate(tripHistoryItemDTO.getArrivalDate())
                .departureAddress(tripHistoryItemDTO.getDepartureAddress())
                .departureDate(tripHistoryItemDTO.getDepartureDate())
                .build();
    }
}
