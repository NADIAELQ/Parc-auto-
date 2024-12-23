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
@Table(name = "gris_card")
public class GrisCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "activated_date", nullable = false)
    private LocalDate activatedDate;
    @OneToOne(mappedBy = "grisCard")
    private Vehicle vehicle;
    public static final int REGISTRATION_VALIDITY_YEARS = 10;
}
