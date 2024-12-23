package com.gl.parcauto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "driver", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cin"})
})
public class Driver {
    @Id
    @Column(name = "cin", updatable = false)
    private String cin;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "driver")
    private Set<DriverLicense> driverLicenses;
    @OneToMany(mappedBy = "driver", cascade = CascadeType.REMOVE)
    private Set<Vacation> vacations;
    @OneToMany(mappedBy = "driver", cascade = CascadeType.REMOVE)
    private Set<Trip> trips;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;
}
