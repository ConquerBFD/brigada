package ec.edu.espe.rancho.dto;



import java.time.LocalDate;

public class RacionPersonalDTO {
    private Integer idPersonal;
    private Integer idqr;
    private String apellidonombre;

    private String cedula;
    private String grado;

    private String unidad;
    private String novedad;
    private String subunidad;
    private LocalDate fecha;
    private Integer desayuno;
    private Integer almuerzo;
    private Integer merienda;

    public RacionPersonalDTO(Integer idPersonal, Integer idqr, String apellidonombre, String cedula, String grado,  String unidad, String novedad, String subunidad, LocalDate fecha, Integer desayuno, Integer almuerzo, Integer merienda) {
        this.idPersonal = idPersonal;
        this.idqr = idqr;
        this.cedula = cedula;
        this.grado = grado;
        this.apellidonombre = apellidonombre;
        this.unidad = unidad;
        this.novedad = novedad;
        this.subunidad = subunidad;
        this.fecha = fecha;
        this.desayuno = desayuno;
        this.almuerzo = almuerzo;
        this.merienda = merienda;
    }

    // Getters y setters
    public Integer getIdPersonal() {
        return idPersonal;
    }
    public void setIdPersonal(Integer idPersonal) {
        this.idPersonal = idPersonal;
    }
    public Integer getIdqr() {
        return idqr;
    }
    public void setIdqr(Integer idqr) {
        this.idqr = idqr;
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
    public String getGrado() {
        return grado;
    }
    public void setGrado(String grado) {
        this.grado = grado;
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

    public String getSubunidad() {
        return subunidad;
    }
    public void setSubunidad(String subunidad) {
        this.subunidad = subunidad;
    }


    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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
}