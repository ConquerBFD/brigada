package ec.edu.espe.rancho.controller;

import ec.edu.espe.rancho.model.Configuracione;
import ec.edu.espe.rancho.service.ConfiguracioneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/configuraciones")
public class ConfiguracioneController {

    private final ConfiguracioneService configuracioneService;

    public ConfiguracioneController(ConfiguracioneService configuracioneService) {
        this.configuracioneService = configuracioneService;
    }

    @PutMapping("/modificar")
    public ResponseEntity<Configuracione> modificarHoraConfiguracion(@RequestBody ConfiguracioneRequest configuracioneRequest) {
        Configuracione modificar = configuracioneService.modificarHoraConfiguracion(
                configuracioneRequest.getTipoConfiguracion(),
                configuracioneRequest.getHora()
        );
        return ResponseEntity.ok(modificar);
    }
    // Endpoint para buscar la hora de configuración por tipo
    //Ejemplo: /api/configuraciones/buscar?tipoConfiguracion=horaApertura
    @GetMapping("/buscar")
    public ResponseEntity<String> buscarHoraConfiguracion(@RequestParam String tipoConfiguracion) {
        String hora = configuracioneService.buscarHoraConfiguracion(tipoConfiguracion).toString();
        return ResponseEntity.ok(hora);
    }

    public static class ConfiguracioneRequest {
        private String tipoConfiguracion;
        private String hora;

        // Getters y setters
        public String getTipoConfiguracion() {
            return tipoConfiguracion;
        }

        public void setTipoConfiguracion(String tipoConfiguracion) {
            this.tipoConfiguracion = tipoConfiguracion;
        }

        public String getHora() {
            return hora;
        }

        public void setHora(String hora) {
            this.hora = hora;
        }
    }
}
