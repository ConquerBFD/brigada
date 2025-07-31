package ec.edu.espe.rancho.repository;

import ec.edu.espe.rancho.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // optional findByNombreusuario
    // This method will return an Optional<Usuario> if a user with the given username exists
    Optional<Usuario> findByNombreusuario(String nombreusuario);
    //exist by Nombreusuario
    boolean existsByNombreusuario(String nombreusuario);
    // find by Nombreusuario



}
