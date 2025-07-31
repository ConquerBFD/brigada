package ec.edu.espe.rancho.service;

import ec.edu.espe.rancho.model.Personal;
import ec.edu.espe.rancho.repository.PersonalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalService {
    private final PersonalRepository personalRepository;
    public PersonalService(PersonalRepository personalRepository) {
        this.personalRepository = personalRepository;
    }
    // Modificar unidad de Personal con cédula
    public Personal modificarUnidadPersonal(String cedula, String unidad) {
        Personal modificar = personalRepository.findByCedula(cedula);
        if (modificar != null) {
            modificar.setUnidad(unidad);
            return personalRepository.save(modificar);
        } else {
            throw new RuntimeException("No se encontró el personal con cédula: " + cedula);
        }
    }
    // Modificar novedad de Personal con cédula
    public Personal modificarNovedadPersonal(String cedula, String novedad) {
        Personal modificar = personalRepository.findByCedula(cedula);
        if (modificar != null) {
            modificar.setNovedad(novedad.toUpperCase());
            return personalRepository.save(modificar);
        } else {
            throw new RuntimeException("No se encontró el personal con cédula: " + cedula);
        }
    }

    // Modificar subunidad de Personal con cédula
    public Personal modificarSubunidadPersonal(String cedula, String subunidad) {
        Personal modificar = personalRepository.findByCedula(cedula);
        if (modificar != null) {
            modificar.setSubunidad(subunidad.toUpperCase());
            return personalRepository.save(modificar);
        } else {
            throw new RuntimeException("No se encontró el personal con cédula: " + cedula);
        }
    }


    // encontrar los nombres de todas las unidades unicas
    public List<String> obtenerUnidadesUnicas() {
        return personalRepository.findDistinctUnidades();
    }
    // Encontrar personal por cédula
    public Personal encontrarPersonalPorCedula(String cedula) {
        Personal personal = personalRepository.findByCedula(cedula);
        if (personal != null) {
            return personal;
        } else {
            throw new RuntimeException("No se encontró el personal con cédula: " + cedula);
        }
    }

    public Personal guardarPersonal(Personal personal) {
        return personalRepository.save(personal);
    }

    public void eliminarPersonal(Personal personal) {
        personalRepository.delete(personal);
    }
}
