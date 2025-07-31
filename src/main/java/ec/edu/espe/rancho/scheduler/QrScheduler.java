package ec.edu.espe.rancho.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ec.edu.espe.rancho.service.QrService;

@Component
public class QrScheduler {

    private final QrService qrService;

    public QrScheduler(QrService qrService) {
        this.qrService = qrService;
    }

    @Scheduled(cron = "0 0 0 1 * ?") // Ejecutar a medianoche el primer día de cada mes
    public void ejecutarActualizacionMensual() {
        qrService.actualizarQrMensualmente();
    }
}