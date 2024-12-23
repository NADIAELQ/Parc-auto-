package com.gl.parcauto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "vacation")
public class Vacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "start_vacation", nullable = false)
    private LocalDateTime start;
    @Column(name = "end_vacation", nullable = false)
    private LocalDateTime end;
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;
}
