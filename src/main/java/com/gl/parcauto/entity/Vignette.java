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
@Table(name = "vignette")
public class Vignette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;
    @OneToOne(mappedBy = "vignette")
    private Vehicle vehicle;
}
