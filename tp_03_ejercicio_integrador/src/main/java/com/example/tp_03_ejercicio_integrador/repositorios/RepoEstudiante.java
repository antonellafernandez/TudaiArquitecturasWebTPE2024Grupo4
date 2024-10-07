package com.example.tp_03_ejercicio_integrador.repositorios;

import com.example.tp_03_ejercicio_integrador.modelos.Estudiante;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("RepoEstudiante")
public interface RepoEstudiante extends RepoBase<Estudiante, Integer> {

    // 2c) Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple. -> Por APELLIDO
    @Query("SELECT e FROM Estudiante e WHERE e.apellido = :apellido")
    List<Estudiante> getEstudiantesByApellido(String apellido);

    // 2d) Eecuperar un estudiante, en base a su número de libreta universitaria.
    @Query("SELECT e FROM Estudiante e WHERE e.lu = :lu")
    List<Estudiante> getEstudianteByLu(String lu);

    // 2e) Recuperar todos los estudiantes, en base a su género.
    @Query("SELECT e FROM Estudiante e WHERE e.genero = :genero")
    List<Estudiante> getEstudianteByGenero(String genero);

    // 2g) Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
    @Query("SELECT e " +
            "FROM Estudiante e " +
            "JOIN e.carrera c " +
            "WHERE e.ciudadResidencia = :ciudadResidencia " +
            "AND c.nombre LIKE :nombreCarrera")
    List<Estudiante> getEstudiantesByCiudad(String ciudadResidencia, String nombreCarrera);
}
