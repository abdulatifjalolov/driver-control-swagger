package com.example.swagger.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeEntity extends BaseEntity{
    private String name;
    private String age;
    private String phoneNumber;
    private String rank;
    @OneToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private DriverEntity driverEntity;

}
