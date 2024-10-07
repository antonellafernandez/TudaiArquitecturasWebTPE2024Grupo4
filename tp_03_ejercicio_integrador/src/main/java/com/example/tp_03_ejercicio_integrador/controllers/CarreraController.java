package springboot.app.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import springboot.app.dtos.CarreraConCantInscriptosDTO;
import springboot.app.dtos.ReporteCarreraDTO;
import springboot.app.modelos.Carrera;
import springboot.app.servicios.CarreraServicio;

import java.util.List;

@RestController
@RequestMapping("/carreras")
public class CarreraController {

    @Autowired
    private CarreraServicio carreraServicio;

    // Crear una nueva carrera
    @PostMapping("/alta")
    public ResponseEntity<?> crearCarrera(@RequestBody Carrera carrera) {
        try {
            Carrera creada = carreraServicio.save(carrera);
            return ResponseEntity.status(HttpStatus.CREATED).body(creada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // Obtener todas las carreras
    @GetMapping("")
    public ResponseEntity<?> obtenerTodasCarreras() {
        try {
            List<Carrera> carreras = carreraServicio.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(carreras);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener carreras.\"}");
        }
    }

    // Obtener una carrera por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCarreraPorId(@PathVariable Long id) {
        try {
            Carrera carrera = carreraServicio.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(carrera);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // Actualizar una carrera
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCarrera(@PathVariable Long id, @RequestBody Carrera carrera) {
        try {
            Carrera actualizada = carreraServicio.update(id, carrera);
            return ResponseEntity.status(HttpStatus.OK).body(actualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // Eliminar una carrera
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCarrera(@PathVariable Long id) {
        try {
            boolean eliminado = carreraServicio.delete(id);
            if (eliminado) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Carrera no encontrada.\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // 2h) Generar un reporte de las carreras
    @GetMapping("/reporte")
    public ResponseEntity<?> generarReporteCarreras() {
        try {
            List<ReporteCarreraDTO> reporte = carreraServicio.generarReporteCarreras();
            return ResponseEntity.status(HttpStatus.OK).body(reporte);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // 2f) Recuperar carreras ordenadas por cantidad de inscriptos
    @GetMapping("/ordenadas-inscriptos")
    public ResponseEntity<?> getCarrerasOrdenadasPorInscriptos() {
        try {
            List<CarreraConCantInscriptosDTO> carreras = carreraServicio.getCarrerasOrdenadasPorInscriptos();
            return ResponseEntity.status(HttpStatus.OK).body(carreras);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
