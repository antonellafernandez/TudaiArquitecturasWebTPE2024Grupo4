package com.example.tp_03_ejercicio_integrador.model;

import jakarta.persistence.*;

@Entity
public class EstudianteCarrera {
    @Id
    private int id;

    // Relación muchos a uno con Estudiante
    @ManyToOne
    @JoinColumn(name = "id_estudiante", nullable = false)
    private Estudiante estudiante;

    // Relación muchos a uno con Carrera
    @ManyToOne
    @JoinColumn(name = "id_carrera", nullable = false)
    private Carrera carrera;

    @Column(nullable = false)
    private int anioInscripcion;

    @Column
    private int anioEgreso;

    @Column
    private int antiguedad;

    private boolean graduado;

    public EstudianteCarrera() {}

    public EstudianteCarrera(int id, Estudiante estudiante, Carrera carrera, int anioInscripcion,
                             int anioEgreso, int antiguedad, boolean graduado) {
        this.id = id;
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.anioInscripcion = anioInscripcion;
        this.anioEgreso = anioEgreso;
        this.antiguedad = antiguedad;
        this.graduado = graduado;
    }

    public int getId() {
        return id;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public int getAnioInscripcion() {
        return anioInscripcion;
    }

    public void setAnioInscripcion(int anioInscripcion) {
        this.anioInscripcion = anioInscripcion;
    }

    public int getAnioEgreso() {
        return anioEgreso;
    }

    public void setAnioEgreso(int anioEgreso) {
        this.anioEgreso = anioEgreso;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public boolean isGraduado() {
        return graduado;
    }

    public void setGraduado(boolean graduado) {
        this.graduado = graduado;
    }

    @Override
    public String toString() {
        return "EstudianteCarrera{" +
                "id=" + id +
                ", carrera=" + carrera +
                ", estudiante=" + estudiante +
                ", anioInscripcion=" + anioInscripcion +
                ", anioEgreso=" + anioEgreso +
                ", antiguedad=" + antiguedad +
                ", graduado=" + graduado +
                '}';
    }
}