package ec.edu.espe.rancho.repository;

import ec.edu.espe.rancho.model.Personal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Integer> {
    //Encontrar personal por cedula
    Personal findByCedula(String cedula);
    //Buscar los nombres de todas las unidades unicas
    @Query("SELECT DISTINCT p.unidad FROM Personal p ORDER BY p.unidad")
    List<String> findDistinctUnidades();
}
