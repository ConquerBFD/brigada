package ec.edu.espe.rancho.repository;

import ec.edu.espe.rancho.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    // Aquí puedes agregar métodos específicos para Rol si es necesario
    // Por ejemplo, encontrar roles por nombre, etc.
    Rol findByNombre(String nombre);

}
