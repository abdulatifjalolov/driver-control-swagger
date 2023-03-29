package com.example.swagger.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "trip_history")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TripHistoryEntity extends BaseEntity {
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private CarEntity carEntity;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "tripHistoryEntity")
    List<TripHistoryItemEntity> tripHistoryItemEntities;


}
