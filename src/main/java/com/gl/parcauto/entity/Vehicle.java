package com.gl.parcauto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "vehicle", uniqueConstraints = {@UniqueConstraint(columnNames = "license_plate")})
public class Vehicle {
    @Id
    @Column(name = "license_plate", updatable = false)
    private String licensePlate;
    @Column(name = "year", nullable = false)
    private Integer year;
    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false)
    private DriverLicenseType type;
    @OneToOne
    @JoinColumn(name = "gris_card_id")
    private GrisCard grisCard;
    @OneToOne
    @JoinColumn(name = "insurance_id")
    private VehicleInsurance vehicleInsurance;
    @OneToOne
    @JoinColumn(name = "vignette_id")
    private Vignette vignette;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle_technical_visit")
    private Set<TechnicalVisit> technicalVisits;
    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY)
    private Set<Trip> tripSet = new HashSet<>();
}
