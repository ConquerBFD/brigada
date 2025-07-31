package ec.edu.espe.rancho.service;

import ec.edu.espe.rancho.model.Precios;
import ec.edu.espe.rancho.repository.PreciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PreciosService {
    @Autowired
    private PreciosRepository preciosRepository;

    public List<Precios> obtenerTodos() {
        return preciosRepository.findAll();
    }

    public Precios modificarPrecio(String comida, Double precio) {
        Optional<Precios> optional = preciosRepository.findById(comida);
        Precios precios = optional.orElseGet(() -> {
            Precios nuevo = new Precios();
            nuevo.setComida(comida);
            return nuevo;
        });
        precios.setPrecio(precio);
        return preciosRepository.save(precios);
    }
}

