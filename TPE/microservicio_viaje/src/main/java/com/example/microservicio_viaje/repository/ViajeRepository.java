package com.example.microservicioviaje.repository;

import com.example.microservicioviaje.entity.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {
//metodos
}
