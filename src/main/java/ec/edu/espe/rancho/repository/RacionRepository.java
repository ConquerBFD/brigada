package ec.edu.espe.rancho.repository;

import ec.edu.espe.rancho.dto.RacionPersonalDTO;
import ec.edu.espe.rancho.model.Qr;
import ec.edu.espe.rancho.model.Racion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RacionRepository extends JpaRepository<Racion, Integer> {

    @Query("SELECT new ec.edu.espe.rancho.dto.RacionPersonalDTO(" +
            "p.id, q.id, p.apellidonombre,p.cedula,p.grado, p.unidad, p.novedad, p.subunidad, r.fecha, r.desayuno, r.almuerzo, r.merienda) " +
            "FROM Racion r " +
            "JOIN r.idqr q " +
            "JOIN q.idusuario u " +
            "JOIN u.idpersonal p " +
            "WHERE r.fecha = :fecha AND p.unidad = :unidad")
    List<RacionPersonalDTO> findAllRacionPersonalByFechaAndUnidad(@Param("fecha") LocalDate fecha, @Param("unidad") String unidad);

    // Encontrar racion por ID de personal y fecha (solo debe ser una)
    @Query("SELECT r FROM Racion r WHERE r.idqr.idusuario.idpersonal.id = :idPersonal AND r.fecha = :fecha")
    Racion findByIdqrIdusuarioIdpersonalIdAndFecha(@Param("idPersonal") Integer idPersonal, @Param("fecha") LocalDate fecha);

    @Query("SELECT q FROM Qr q WHERE q.idusuario.idpersonal.id IN :idsPersonal AND q.isActive = 1")
    List<Qr> findActiveQrByIdPersonalIn(@Param("idsPersonal") List<Integer> idsPersonal);

    @Query("SELECT r.idqr.idusuario.idpersonal.id FROM Racion r WHERE r.fecha = :fecha AND r.idqr.idusuario.idpersonal.id IN :ids")
    List<Integer> findPersonalIdsConRacionEnFecha(@Param("fecha") LocalDate fecha, @Param("ids") List<Integer> ids);

    @Query("SELECT r FROM Racion r WHERE r.fecha = :fecha AND r.idqr.idusuario.idpersonal.id IN :idsPersonal")
    List<Racion> findAllByFechaAndIdPersonalIn(@Param("fecha") LocalDate fecha, @Param("idsPersonal") List<Integer> idsPersonal);

    @Query("SELECT r FROM Racion r WHERE r.fecha = :fecha")
    List<Racion> findAllByFecha(@Param("fecha") LocalDate fecha);

    @Query("SELECT r FROM Racion r WHERE r.idqr.id = :idqr AND r.fecha = :fecha")
    Racion findByIdqrIdAndFecha(@Param("idqr") Integer idqr, @Param("fecha") java.time.LocalDate fecha);

    List<Racion> findAllByIdqr(Qr idqr);
}
