package ec.edu.espe.rancho.service;

import ec.edu.espe.rancho.model.Qr;
import ec.edu.espe.rancho.repository.QrRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class QrService {

    private final QrRepository qrRepository;

    public QrService(QrRepository qrRepository) {
        this.qrRepository = qrRepository;
    }

    @Transactional
    public void actualizarQrMensualmente() {
        LocalDate fechaActual = LocalDate.now();
        if (fechaActual.getDayOfMonth() == 1) { // Verificar si es el primer día del mes
            List<Qr> qrActivos = qrRepository.findAllActiveQr();
            qrActivos.forEach(qr -> qr.setQrData(UUID.randomUUID().toString())); // Generar nuevo UUID para cada QR activo
            qrRepository.saveAll(qrActivos); // Guardar los cambios en la base de datos
        }
    }
}