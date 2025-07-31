package ec.edu.espe.rancho.service;

import ec.edu.espe.rancho.model.Rol;
import ec.edu.espe.rancho.model.Usuario;
import ec.edu.espe.rancho.repository.RolRepository;
import ec.edu.espe.rancho.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolService {
    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;

    public RolService(RolRepository rolRepository, UsuarioRepository usuarioRepository) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Crear un nuevo rol por nombre
    public void crearRol(String nombre) {
        if (rolRepository.findByNombre(nombre) == null) {
            rolRepository.save(new Rol(nombre));
        } else {
            throw new RuntimeException("El rol con nombre " + nombre + " ya existe.");
        }
    }

    // Modificar nombre de rol por nombre
    public Rol modificarNombreRol(String nombreActual, String nuevoNombre) {
        Rol rol = rolRepository.findByNombre(nombreActual);
        if (rol != null) {
            rol.setNombre(nuevoNombre);
            return rolRepository.save(rol);
        } else {
            throw new RuntimeException("No se encontró el rol con nombre: " + nombreActual);
        }
    }

    // Cambiar rol de usuario por nombre de usuario y nuevo nombre de rol
    public void cambiarRolUsuario(String nombreUsuario, String nuevoNombreRol) {
        Rol nuevoRol = rolRepository.findByNombre(nuevoNombreRol);
        if (nuevoRol == null) {
            throw new RuntimeException("No se encontró el rol con nombre: " + nuevoNombreRol);
        }

        Optional<Usuario> optionalUsuario = usuarioRepository.findByNombreusuario(nombreUsuario);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setIdrol(nuevoRol);
            usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("No se encontró el usuario con nombre: " + nombreUsuario);
        }
    }
    //Encontrar los nombres de todos los roles en json
    public List<String> obtenerNombresRoles() {
        List<Rol> roles = rolRepository.findAll();
        if (roles.isEmpty()) {
            throw new RuntimeException("No se encontraron roles.");
        }
        return roles.stream().map(Rol::getNombre).collect(Collectors.toList());
    }
}