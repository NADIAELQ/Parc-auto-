package com.gl.parcauto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "driver_license", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"license_type", "driver_id"})
})
public class DriverLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "delivery_date", nullable = false)
    private LocalDateTime deliveryDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "license_type", nullable = false)
    private DriverLicenseType licenseType;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;
    // prevent a driver to have two driver licenses with the same type
    public static final int EXPIRE_DATE_BY_YEAR = 10;
}