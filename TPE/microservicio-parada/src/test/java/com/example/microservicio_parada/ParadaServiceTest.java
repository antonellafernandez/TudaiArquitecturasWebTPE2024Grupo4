package com.example.microservicio_parada;

import com.example.microservicio_parada.entity.Parada;
import com.example.microservicio_parada.repository.ParadaRepository;
import com.example.microservicio_parada.service.ParadaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ParadaServiceTest {

    @Mock
    private ParadaRepository paradaRepository;

    @InjectMocks
    private ParadaService paradaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveParada() {
        Parada parada = new Parada();
        parada.setNombre("Parada Test");

        when(paradaRepository.save(parada)).thenReturn(parada);

        Parada result = paradaService.save(parada);
        assertEquals("Parada Test", result.getNombre());
    }

    @Test
    public void testGetAllParadas() {
        when(paradaRepository.findAll()).thenReturn(Collections.emptyList());

        assertTrue(paradaService.getAll().isEmpty());
    }

    @Test
    public void testFindById() {
        Parada parada = new Parada();
        parada.setId(1L);

        when(paradaRepository.findById(1L)).thenReturn(Optional.of(parada));

        Parada result = paradaService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testDeleteParada() {
        Parada parada = new Parada();
        parada.setId(1L);

        doNothing().when(paradaRepository).delete(parada);

        paradaService.delete(parada);

        verify(paradaRepository, times(1)).delete(parada);
    }
}