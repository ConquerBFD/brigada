package ec.edu.espe.rancho.controller;

import ec.edu.espe.rancho.model.Qr;
import ec.edu.espe.rancho.model.Usuario;
import ec.edu.espe.rancho.model.Personal;
import ec.edu.espe.rancho.model.Racion;
import ec.edu.espe.rancho.repository.QrRepository;
import ec.edu.espe.rancho.repository.RacionRepository;
import ec.edu.espe.rancho.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/qr")
public class QrController {
    @Autowired
    private QrRepository qrRepository;
    @Autowired
    private RacionRepository racionRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/info")
    public ResponseEntity<?> obtenerInfoPorQrData(@RequestBody QrDataRequest request) {
        Qr qr = qrRepository.findByQrData(request.getQrData());
        if (qr == null) {
            return ResponseEntity.status(404).body("QR no encontrado");
        }
        Usuario usuario = qr.getIdusuario();
        if (usuario == null || usuario.getIdpersonal() == null) {
            return ResponseEntity.status(404).body("Usuario o personal no encontrado");
        }
        Personal personal = usuario.getIdpersonal();
        LocalDate hoy = LocalDate.now();
        Racion racion = racionRepository.findByIdqrIdAndFecha(qr.getId(), hoy);
        Map<String, Object> response = new HashMap<>();
        response.put("personal", personal);
        response.put("racion", racion);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/consumir-racion")
    public ResponseEntity<?> consumirRacion(@RequestBody ConsumirRacionRequest request) {
        LocalDate hoy = LocalDate.now();
        Racion racion = racionRepository.findByIdqrIdAndFecha(request.getIdqr(), hoy);
        if (racion == null) {
            // Si no existe, crear una nueva ración con valores en 0
            racion = new Racion();
            racion.setIdqr(qrRepository.findById(request.getIdqr()).orElse(null));
            racion.setFecha(hoy);
            racion.setDesayuno(0);
            racion.setAlmuerzo(0);
            racion.setMerienda(0);
        }
        // Validar que la ración solo pueda ser consumida si es 1
        boolean puedeConsumir = false;
        switch (request.getRacion().toLowerCase()) {
            case "desayuno":
                puedeConsumir = (racion.getDesayuno() == 1);
                break;
            case "almuerzo":
                puedeConsumir = (racion.getAlmuerzo() == 1);
                break;
            case "merienda":
                puedeConsumir = (racion.getMerienda() == 1);
                break;
            default:
                return ResponseEntity.badRequest().body("Tipo de ración inválido");
        }
        if (!puedeConsumir) {
            return ResponseEntity.badRequest().body("La ración no puede ser consumida. Debe estar en estado 1 antes de consumirse.");
        }
        // Actualizar solo la ración indicada
        switch (request.getRacion().toLowerCase()) {
            case "desayuno":
                racion.setDesayuno(-1);
                break;
            case "almuerzo":
                racion.setAlmuerzo(-1);
                break;
            case "merienda":
                racion.setMerienda(-1);
                break;
        }
        racionRepository.save(racion);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("racion", racion);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/usuario/{nombreusuario}")
    public ResponseEntity<?> obtenerQrYPersonalPorNombreusuario(@PathVariable String nombreusuario) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByNombreusuario(nombreusuario);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
        Usuario usuario = usuarioOpt.get();
        Personal personal = usuario.getIdpersonal();
        if (personal == null) {
            return ResponseEntity.status(404).body("Personal no encontrado para el usuario");
        }
        Qr qr = qrRepository.findByIdusuario_Id(usuario.getId());
        if (qr == null) {
            return ResponseEntity.status(404).body("QR no encontrado para el usuario");
        }
        Map<String, Object> response = new HashMap<>();
        response.put("qr", qr);
        response.put("personal", personal);
        return ResponseEntity.ok(response);
    }

    public static class QrDataRequest {
        private String qrData;
        public String getQrData() { return qrData; }
        public void setQrData(String qrData) { this.qrData = qrData; }
    }

    public static class ConsumirRacionRequest {
        private Integer idqr;
        private String racion;
        public Integer getIdqr() { return idqr; }
        public void setIdqr(Integer idqr) { this.idqr = idqr; }
        public String getRacion() { return racion; }
        public void setRacion(String racion) { this.racion = racion; }
    }
}
