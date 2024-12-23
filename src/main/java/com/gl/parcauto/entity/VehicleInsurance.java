package com.gl.parcauto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "vehicle_insurance")
public class VehicleInsurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "activated_date", nullable = false)
    private LocalDate activatedDate;
    @Enumerated(EnumType.STRING)
    private InsuranceDuration duration;
    @OneToOne(mappedBy = "vehicleInsurance")
    private Vehicle vehicle;
}

