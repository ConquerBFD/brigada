package ec.edu.espe.rancho.service;

import ec.edu.espe.rancho.model.Configuracione;
import ec.edu.espe.rancho.repository.ConfiguracioneRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class ConfiguracioneService {
    private final ConfiguracioneRepository configuracioneRepository;
    public ConfiguracioneService(ConfiguracioneRepository configuracioneRepository) {
        this.configuracioneRepository = configuracioneRepository;
    }
    // Modificar hora de configuración por tipo
    public Configuracione modificarHoraConfiguracion(String tipoConfiguracion, String hora) {
        Configuracione modificar = configuracioneRepository.findByTipoConfiguracion(tipoConfiguracion);
        if (modificar != null) {
            modificar.setHora(LocalTime.parse(hora));
            return configuracioneRepository.save(modificar);
        } else {
            throw new RuntimeException("No se encontró la configuración con tipo: " + tipoConfiguracion);
        }
    }
    // Buscar hora de configuración por tipo
    public LocalTime buscarHoraConfiguracion(String tipoConfiguracion) {
        Configuracione configuracion = configuracioneRepository.findByTipoConfiguracion(tipoConfiguracion);
        if (configuracion != null) {
            return configuracion.getHora();
        } else {
            throw new RuntimeException("No se encontró la configuración con tipo: " + tipoConfiguracion);
        }
    }
}
