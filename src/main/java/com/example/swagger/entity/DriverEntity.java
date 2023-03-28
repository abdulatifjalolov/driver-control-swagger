package com.example.swagger.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DriverEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private Integer age;
    private String address;
    @Column(nullable = false)
    private Integer workExperience;
    @Column(nullable = false)
    private String certificateCategory;
    @OneToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private CarEntity carEntity;
}
