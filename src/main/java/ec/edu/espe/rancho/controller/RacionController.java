package ec.edu.espe.rancho.controller;

import ec.edu.espe.rancho.dto.RacionPersonalDTO;
import ec.edu.espe.rancho.dto.RacionSimpleDTO;
import ec.edu.espe.rancho.model.Racion;
import ec.edu.espe.rancho.service.RacionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/raciones")
public class RacionController {

    private final RacionService racionService;

    public RacionController(RacionService racionService) {
        this.racionService = racionService;
    }

    // Endpoint para obtener raciones por fecha y unidad
    // ejemplo: /api/raciones?fecha=2023-10-01&unidad=Unidad1
    @GetMapping
    public ResponseEntity<List<RacionPersonalDTO>> obtenerRacionesPorFecha(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("unidad") String unidad
    ) {
        List<RacionPersonalDTO> raciones = racionService.obtenerRacionesPorFecha(fecha, unidad);
        return ResponseEntity.ok(raciones);
    }

    @PostMapping
    public ResponseEntity<List<RacionSimpleDTO>> crearRaciones(@RequestBody List<RacionRequest> requests) {
        List<RacionSimpleDTO> racionesCreadas = racionService.guardarOActualizarRaciones(requests);
        return ResponseEntity.ok(racionesCreadas);
    }

    @GetMapping("/consumidas")
    public ResponseEntity<Map<String, Map<String, Integer>>> obtenerRacionesConsumidasPorUnidad(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        Map<String, Map<String, Integer>> resultado = racionService.obtenerRacionesConsumidasPorUnidad(fecha);
        return ResponseEntity.ok(resultado);
    }

    public static class RacionRequest {
        private Integer idPersonal;
        private Integer idqr;
        private int desayuno;
        private int almuerzo;
        private int merienda;

        public Integer getIdPersonal() {
            return idPersonal;
        }

        public void setIdPersonal(Integer idPersonal) {
            this.idPersonal = idPersonal;
        }
        public Integer getIdqr() {
            return idqr;
        }
        public void setIdqr(Integer idqr) {
            this.idqr = idqr;
        }

        public int getDesayuno() {
            return desayuno;
        }

        public void setDesayuno(int desayuno) {
            this.desayuno = desayuno;
        }

        public int getAlmuerzo() {
            return almuerzo;
        }

        public void setAlmuerzo(int almuerzo) {
            this.almuerzo = almuerzo;
        }

        public int getMerienda() {
            return merienda;
        }

        public void setMerienda(int merienda) {
            this.merienda = merienda;
        }
    }

    public static class RacionFechaUnidadRequest {
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate fecha;
        private String unidad;

        public LocalDate getFecha() {
            return fecha;
        }

        public void setFecha(LocalDate fecha) {
            this.fecha = fecha;
        }

        public String getUnidad() {
            return unidad;
        }

        public void setUnidad(String unidad) {
            this.unidad = unidad;
        }
    }
}
