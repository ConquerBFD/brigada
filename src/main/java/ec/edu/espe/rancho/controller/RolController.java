package ec.edu.espe.rancho.controller;

import ec.edu.espe.rancho.model.Rol;
import ec.edu.espe.rancho.service.RolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    // Endpoint para crear un nuevo rol
    @PostMapping("/crear")
    public ResponseEntity<String> crearRol(@RequestBody crearRolRequest request) {
        String nuevoRol = request.getNombre();
        try {
            rolService.crearRol(nuevoRol);
            return ResponseEntity.ok("Rol creado exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para modificar el nombre de un rol
    @PutMapping("/modificar")
    public ResponseEntity<?> modificarNombreRol(@RequestBody modificarNombreRolRequest request) {
        String nombreActual = request.getNombreActual();
        String nuevoNombre = request.getNuevoNombre();
        try {
            Rol rolActualizado = rolService.modificarNombreRol(nombreActual, nuevoNombre);
            return ResponseEntity.ok(rolActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint para cambiar el rol de un usuario
    @PutMapping("/cambiar-rol-usuario")
    public ResponseEntity<String> cambiarRolUsuario(@RequestBody cambiarRolUsuarioRequest request) {
        String nombreUsuario = request.getNombreUsuario();
        String nuevoRol = request.getNuevoRol();
        try {
            rolService.cambiarRolUsuario(nombreUsuario, nuevoRol);
            return ResponseEntity.ok("Rol del usuario actualizado correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //Endpoint para obtener los nombres de todos los roles en json
    @GetMapping("/nombres")
    public ResponseEntity<?> obtenerNombresRoles() {
        try {
            return ResponseEntity.ok(rolService.obtenerNombresRoles());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public static class crearRolRequest {
        private String nombre;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
    }
    public static class modificarNombreRolRequest {
        private String nombreActual;
        private String nuevoNombre;

        public String getNombreActual() {
            return nombreActual;
        }

        public void setNombreActual(String nombreActual) {
            this.nombreActual = nombreActual;
        }

        public String getNuevoNombre() {
            return nuevoNombre;
        }

        public void setNuevoNombre(String nuevoNombre) {
            this.nuevoNombre = nuevoNombre;
        }
    }

    public static class cambiarRolUsuarioRequest {
        private String nombreUsuario;
        private String nuevoRol;

        public String getNombreUsuario() {
            return nombreUsuario;
        }

        public void setNombreUsuario(String nombreUsuario) {
            this.nombreUsuario = nombreUsuario;
        }

        public String getNuevoRol() {
            return nuevoRol;
        }

        public void setNuevoRol(String nuevoRol) {
            this.nuevoRol = nuevoRol;
        }
    }
}
