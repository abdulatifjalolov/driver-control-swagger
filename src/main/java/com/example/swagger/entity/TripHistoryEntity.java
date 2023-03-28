package com.example.swagger.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TripHistoryEntity extends BaseEntity {
    @OneToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private CarEntity carEntity;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "tripHistoryEntity")
    private List<TripHistoryItemEntity> tripHistoryItemEntity;
}
