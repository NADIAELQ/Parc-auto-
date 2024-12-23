package com.gl.parcauto.repository;

import com.gl.parcauto.entity.Vignette;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VignetteRepository extends JpaRepository<Vignette, Long> {
}