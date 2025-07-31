package ec.edu.espe.rancho.service;


import ec.edu.espe.rancho.model.Personal;
import ec.edu.espe.rancho.model.Rol;
import ec.edu.espe.rancho.model.Usuario;
import ec.edu.espe.rancho.repository.PersonalRepository;
import ec.edu.espe.rancho.repository.RolRepository;
import ec.edu.espe.rancho.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PersonalRepository personalRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void generarUsuarios() {
        List<Personal> listaPersonal = personalRepository.findAll();
        Rol rolUsuario = rolRepository.findByNombre("usuario");

        for (Personal p : listaPersonal) {
            if (!usuarioRepository.existsByNombreusuario(p.getCedula())) {
                Usuario u = new Usuario();
                u.setNombreusuario(p.getCedula());
                u.setContrasena(passwordEncoder.encode(p.getCedula()));
                u.setIdpersonal(p);
                u.setIdrol(rolUsuario);
                usuarioRepository.save(u);
            }
        }
    }

    public boolean cambiarContrasena(String nombreusuario, String nuevaContrasena) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByNombreusuario(nombreusuario);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setContrasena(passwordEncoder.encode(nuevaContrasena));
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

}
