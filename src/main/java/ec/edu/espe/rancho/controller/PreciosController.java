package ec.edu.espe.rancho.controller;

import ec.edu.espe.rancho.model.Precios;
import ec.edu.espe.rancho.service.PreciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/precios")
public class PreciosController {
    @Autowired
    private PreciosService preciosService;

    @GetMapping
    public ResponseEntity<List<Precios>> obtenerTodos() {
        return ResponseEntity.ok(preciosService.obtenerTodos());
    }

    @PutMapping("/modificar")
    public ResponseEntity<Precios> modificarPrecio(@RequestBody ModificarPrecioRequest request) {
        Precios actualizado = preciosService.modificarPrecio(request.getComida(), request.getPrecio());
        return ResponseEntity.ok(actualizado);
    }

    public static class ModificarPrecioRequest {
        private String comida;
        private Double precio;

        public String getComida() {
            return comida;
        }
        public void setComida(String comida) {
            this.comida = comida;
        }
        public Double getPrecio() {
            return precio;
        }
        public void setPrecio(Double precio) {
            this.precio = precio;
        }
    }
}

