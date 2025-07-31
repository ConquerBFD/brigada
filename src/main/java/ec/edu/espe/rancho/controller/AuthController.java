package ec.edu.espe.rancho.controller;

import ec.edu.espe.rancho.model.Usuario;
import ec.edu.espe.rancho.repository.UsuarioRepository;
import ec.edu.espe.rancho.service.UsuarioService;
import ec.edu.espe.rancho.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/generarusuarios")
    public ResponseEntity<String> generarUsuarios() {
        usuarioService.generarUsuarios();
        return ResponseEntity.ok("Usuarios generados");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Usuario> optUsuario = usuarioRepository.findByNombreusuario(request.getNombreusuario());
        if (optUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }

        Usuario usuario = optUsuario.get();

        if (!passwordEncoder.matches(request.getConstrasena(), usuario.getContrasena())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }

        String token = jwtUtil.generateToken(usuario.getNombreusuario(), usuario.getIdrol().getNombre());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("usuario", usuario.getNombreusuario());
        response.put("rol", usuario.getIdrol().getNombre());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/cambiar-contrasena")
    public ResponseEntity<?> cambiarContrasena(@RequestBody CambiarContrasenaRequest request) {
        boolean actualizado = usuarioService.cambiarContrasena(request.getNombreusuario(), request.getNuevaContrasena());
        if (actualizado) {
            return ResponseEntity.ok("Contraseña actualizada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    public static class LoginRequest {
        private String nombreusuario;
        private String constrasena;

        // Getters y setters
        public String getNombreusuario() {
            return nombreusuario;
        }

        public void setNombreusuario(String nombreusuario) {
            this.nombreusuario = nombreusuario;
        }

        public String getConstrasena() {
            return constrasena;
        }

        public void setConstrasena(String constrasena) {
            this.constrasena = constrasena;
        }
    }

    public static class CambiarContrasenaRequest {
        private String nombreusuario;
        private String nuevaContrasena;

        public String getNombreusuario() {
            return nombreusuario;
        }

        public void setNombreusuario(String nombreusuario) {
            this.nombreusuario = nombreusuario;
        }

        public String getNuevaContrasena() {
            return nuevaContrasena;
        }

        public void setNuevaContrasena(String nuevaContrasena) {
            this.nuevaContrasena = nuevaContrasena;
        }
    }
}
