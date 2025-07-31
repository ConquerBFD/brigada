package ec.edu.espe.rancho.repository;

import ec.edu.espe.rancho.model.Configuracione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracioneRepository extends JpaRepository<Configuracione, String> {
    // buscar por tipo de configuracion
    Configuracione findByTipoConfiguracion(String tipoConfiguracion);
}


