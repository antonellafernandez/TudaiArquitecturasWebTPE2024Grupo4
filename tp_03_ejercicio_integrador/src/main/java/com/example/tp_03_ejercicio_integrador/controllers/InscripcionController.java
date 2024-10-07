package springboot.app.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import springboot.app.dtos.EstudianteCarreraDTO; // Cambiar aquí
import springboot.app.modelos.EstudianteCarrera;
import springboot.app.servicios.MatriculacionServicio;

import java.util.List;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    @Autowired
    private MatriculacionServicio matriculacionServicio;

    // b) Matricular un estudiante en una carrera
    @PostMapping("/matricular")
    public ResponseEntity<?> matricularEstudiante(@RequestBody EstudianteCarreraDTO estudianteCarreraDTO) {
        try {
            EstudianteCarrera inscripcion = matriculacionServicio.matricularEstudiante(estudianteCarreraDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(inscripcion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // Obtener todas las inscripciones
    @GetMapping("")
    public ResponseEntity<?> obtenerTodasInscripciones() {
        try {
            List<EstudianteCarrera> inscripciones = matriculacionServicio.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(inscripciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener inscripciones: " + e.getMessage() + "\"}");
        }
    }

    // Obtener una inscripción por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerInscripcionPorId(@PathVariable Long id) {
        try {
            EstudianteCarrera inscripcion = matriculacionServicio.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(inscripcion);
        } catch (Exception e) {
            return ResponseEntity.status(H