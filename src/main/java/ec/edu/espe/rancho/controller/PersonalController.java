package ec.edu.espe.rancho.controller;

import ec.edu.espe.rancho.model.Personal;
import ec.edu.espe.rancho.model.Usuario;
import ec.edu.espe.rancho.model.Rol;
import ec.edu.espe.rancho.model.Qr;
import ec.edu.espe.rancho.model.Racion;
import ec.edu.espe.rancho.repository.UsuarioRepository;
import ec.edu.espe.rancho.repository.RolRepository;
import ec.edu.espe.rancho.repository.QrRepository;
import ec.edu.espe.rancho.repository.RacionRepository;
import ec.edu.espe.rancho.service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.UUID;
import java.util.Map;

@RestController
@RequestMapping("/api/personal")
public class PersonalController {
    private final PersonalService personalService;
    public PersonalController(PersonalService personalService) {
        this.personalService = personalService;
    }
    @PutMapping
    //Pide de request body la cedula y la unidad
    public Personal actualizarPersonal(@RequestBody PersonalRequest personalRequest) {
        Personal modificar = personalService.modificarUnidadPersonal(personalRequest.getCedula(), personalRequest.getUnidad());
        return ok(modificar).getBody();
    }
    @PutMapping("/novedad")
    //Pide de request body la cedula y la novedad
    public Personal actualizarNovedad(@RequestBody NovedadRequest novedadRequest) {
        Personal modificar = personalService.modificarNovedadPersonal(novedadRequest.getCedula(), novedadRequest.getNovedad().toUpperCase());
        return ok(modificar).getBody();
    }
    @PutMapping("/subunidad")
    //Pide de request body la cedula y la subunidad
    public Personal actualizarSubunidad(@RequestBody SubunidadRequest subunidadRequest) {
        Personal modificar = personalService.modificarSubunidadPersonal(subunidadRequest.getCedula(), subunidadRequest.getSubunidad());
        return ok(modificar).getBody();
    }


    // Endpoint para obtener las unidades unicas
    @GetMapping("/unidades")
    public java.util.List<String> obtenerUnidadesUnicas() {
        return personalService.obtenerUnidadesUnicas();
    }
    // Endpoint para encontrar personal por cedula
    // Ejemplo: /api/personal/buscar?cedula=1234567890
    @GetMapping("/buscar")
    public Personal encontrarPersonalPorCedula(@RequestParam String cedula) {
        return personalService.encontrarPersonalPorCedula(cedula);
    }
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private QrRepository qrRepository;
    @Autowired
    private RacionRepository racionRepository;

    @PostMapping("/agregar")
    public ResponseEntity<?> agregarPersonal(@RequestBody NuevoPersonalRequest request) {
        // Validar campos obligatorios
        if (request.getCedula() == null || request.getApellidonombre() == null || request.getUnidad() == null || request.getGrado() == null) {
            return ResponseEntity.badRequest().body("Faltan campos obligatorios");
        }
        // Verificar si ya existe usuario con esa cédula
        if (usuarioRepository.existsByNombreusuario(request.getCedula())) {
            return ResponseEntity.status(409).body("Ya existe un usuario con esa cédula");
        }
        // Crear Personal
        Personal nuevo = new Personal();
        nuevo.setCedula(request.getCedula());
        nuevo.setApellidonombre(request.getApellidonombre());
        nuevo.setUnidad(request.getUnidad());
        nuevo.setGrado(request.getGrado());
        if (request.getNovedad() != null) nuevo.setNovedad(request.getNovedad());
        if (request.getCelular() != null) nuevo.setCelular(request.getCelular());
        if (request.getOrden() != null) nuevo.setOrden(request.getOrden());
        if (request.getSubunidad() != null) nuevo.setSubunidad(request.getSubunidad());
        Personal guardado = personalService.guardarPersonal(nuevo);
        // Crear Usuario
        Rol rolUsuario = rolRepository.findByNombre("usuario");
        Usuario usuario = new Usuario();
        usuario.setNombreusuario(request.getCedula());
        usuario.setContrasena(passwordEncoder.encode(request.getCedula()));
        usuario.setIdpersonal(guardado);
        usuario.setIdrol(rolUsuario);
        usuarioRepository.save(usuario);
        // Crear QR para el usuario
        Qr qr = new Qr();
        qr.setIdusuario(usuario);
        qr.setQrData(UUID.randomUUID().toString());
        LocalDate now = LocalDate.now();
        qr.setCreatedAt(now);
        YearMonth ym = YearMonth.of(now.getYear(), now.getMonth());
        qr.setExpirationDate(ym.atEndOfMonth());
        qr.setIsActive((byte)1);
        qrRepository.save(qr);
        // Crear ración inicial (todo en 1) para el QR nuevo y la fecha actual
        Racion racion = new Racion();
        racion.setIdqr(qr);
        racion.setFecha(LocalDate.now());
        racion.setDesayuno(1);
        racion.setAlmuerzo(1);
        racion.setMerienda(1);
        racionRepository.save(racion);
        // Respuesta con todo
        Map<String, Object> response = new java.util.HashMap<>();
        response.put("personal", guardado);
        response.put("usuario", usuario);
        response.put("qr", qr);
        response.put("racion", racion);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarPersonal(@RequestParam String cedula) {
        // Buscar personal
        Personal personal = personalService.encontrarPersonalPorCedula(cedula);
        if (personal == null) {
            return ResponseEntity.status(404).body("Personal no encontrado");
        }
        // Buscar usuario
        Usuario usuario = usuarioRepository.findByNombreusuario(cedula).orElse(null);
        if (usuario != null) {
            // Buscar QR
            Qr qr = qrRepository.findByIdusuario_Id(usuario.getId());
            if (qr != null) {
                // Eliminar raciones asociadas al QR
                java.util.List<Racion> raciones = racionRepository.findAllByIdqr(qr);
                racionRepository.deleteAll(raciones);
                // Eliminar QR
                qrRepository.delete(qr);
            }
            // Eliminar usuario
            usuarioRepository.delete(usuario);
        }
        // Eliminar personal
        personalService.eliminarPersonal(personal);
        return ResponseEntity.ok("Personal y todos sus datos eliminados correctamente");
    }

    public static class NovedadRequest {
        private String cedula;
        private String novedad;

        // Getters y setters
        public String getCedula() {
            return cedula;
        }

        public void setCedula(String cedula) {
            this.cedula = cedula;
        }

        public String getNovedad() {
            return novedad;
        }

        public void setNovedad(String novedad) {
            this.novedad = novedad;
        }
    }
    public static class PersonalRequest {
        private String cedula;
        private String unidad;

        // Getters y setters
        public String getCedula() {
            return cedula;
        }

        public void setCedula(String cedula) {
            this.cedula = cedula;
        }

        public String getUnidad() {
            return unidad;
        }

        public void setUnidad(String unidad) {
            this.unidad = unidad;
        }
    }

    public static class SubunidadRequest {
        private String cedula;
        private String subunidad;

        // Getters y setters
        public String getCedula() {
            return cedula;
        }

        public void setCedula(String cedula) {
            this.cedula = cedula;
        }

        public String getSubunidad() {
            return subunidad;
        }

        public void setSubunidad(String subunidad) {
            this.subunidad = subunidad;
        }
    }


    public static class NuevoPersonalRequest {
        private String cedula;
        private String apellidonombre;
        private String unidad;
        private String grado;
        private String novedad;
        private String celular;
        private Integer orden;
        private String subunidad;
        // Getters y setters
        public String getCedula() { return cedula; }
        public void setCedula(String cedula) { this.cedula = cedula; }
        public String getApellidonombre() { return apellidonombre; }
        public void setApellidonombre(String apellidonombre) { this.apellidonombre = apellidonombre; }
        public String getUnidad() { return unidad; }
        public void setUnidad(String unidad) { this.unidad = unidad; }
        public String getGrado() { return grado; }
        public void setGrado(String grado) { this.grado = grado; }
        public String getNovedad() { return novedad; }
        public void setNovedad(String novedad) { this.novedad = novedad; }
        public String getCelular() { return celular; }
        public void setCelular(String celular) { this.celular = celular; }
        public Integer getOrden() { return orden; }
        public void setOrden(Integer orden) { this.orden = orden; }
        public String getSubunidad() { return subunidad; }
        public void setSubunidad(String subunidad) { this.subunidad = subunidad; }
    }
}
