package ec.edu.espe.rancho.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "personal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Personal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpersonal", nullable = false)
    private Integer id;

    @Column(name = "cedula", nullable = false, length = 10)
    private String cedula;

    @Column(name = "apellidonombre", nullable = false, length = 100)
    private String apellidonombre;

    @Column(name = "unidad", nullable = false, length = 45)
    private String unidad;

    @Column(name = "novedad", length = 45)
    private String novedad;

    @Column(name= "grado", length = 45)
    private String grado;

    @Column(name = "celular", length = 20)
    private String celular;

    @Column(name = "orden")
    private Integer orden;

    @Column(name = "subunidad" , length = 45)
    private String subunidad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getApellidonombre() {
        return apellidonombre;
    }

    public void setApellidonombre(String apellidonombre) {
        this.apellidonombre = apellidonombre;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getNovedad() {
        return novedad;
    }

    public void setNovedad(String novedad) {
        this.novedad = novedad;
    }

    public String getGrado() {
        return grado;
    }
    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getCelular() {
        return celular;
    }
    public void setCelular(String celular) {
        this.celular = celular;
    }
    public Integer getOrden() {
        return orden;
    }
    public void setOrden(Integer orden) {
        this.orden = orden;
    }
    public String getSubunidad() {
        return subunidad;
    }
    public void setSubunidad(String subunidad) {
        this.subunidad = subunidad;
    }



}