package com.example.swagger.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TripHistoryItemEntity extends BaseEntity {
    @Column(nullable = false)
    private Timestamp departureDate;
    @Column(nullable = false)
    private String departureAddress;
    @Column(nullable = false)
    private Timestamp arrivalDate;
    @Column(nullable = false)
    private String arrivalAddress;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private TripHistoryEntity tripHistoryEntity;
}
