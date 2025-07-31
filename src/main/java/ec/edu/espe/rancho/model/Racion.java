package ec.edu.espe.rancho.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity


@Table(name = "racion")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Racion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idracion", nullable = false)
    private Integer id;

    @Column(name = "desayuno", nullable = false)
    private Integer desayuno;

    @Column(name = "almuerzo", nullable = false)
    private Integer almuerzo;

    @Column(name = "merienda", nullable = false)
    private Integer merienda;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idqr", nullable = false)
    private Qr idqr;

    public Racion() {
    }
    public Racion(Qr idqr, Integer desayuno, Integer almuerzo, Integer merienda) {
        this.idqr = idqr;
        this.desayuno = desayuno;
        this.almuerzo = almuerzo;
        this.merienda = merienda;
        this.fecha = LocalDate.now();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDesayuno() {
        return desayuno;
    }

    public void setDesayuno(Integer desayuno) {
        this.desayuno = desayuno;
    }

    public Integer getAlmuerzo() {
        return almuerzo;
    }

    public void setAlmuerzo(Integer almuerzo) {
        this.almuerzo = almuerzo;
    }

    public Integer getMerienda() {
        return merienda;
    }

    public void setMerienda(Integer merienda) {
        this.merienda = merienda;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Qr getIdqr() {
        return idqr;
    }

    public void setIdqr(Qr idqr) {
        this.idqr = idqr;
    }

}