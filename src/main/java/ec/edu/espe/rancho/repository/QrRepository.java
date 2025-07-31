package ec.edu.espe.rancho.repository;


import ec.edu.espe.rancho.model.Qr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QrRepository extends JpaRepository<Qr, Integer> {

    // obtener un QR por ID de personal
    @Query("SELECT q FROM Qr q WHERE q.idusuario.idpersonal.id IN :idsPersonal AND q.isActive = 1")
    List<Qr> findActiveQrByIdPersonalIn(@Param("idsPersonal") List<Integer> idsPersonal);
    //Obtener todos los QRs activos
    @Query("SELECT q FROM Qr q WHERE q.isActive = 1")
    List<Qr> findAllActiveQr();
    Qr findByQrData(String qrData);
    Qr findByIdusuario_Id(Integer idusuario);
}
