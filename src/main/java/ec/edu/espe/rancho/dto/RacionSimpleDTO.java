package ec.edu.espe.rancho.dto;

public class RacionSimpleDTO {
    private Integer id;
    private Integer idqr;
    private Integer idPersonal;
    private int desayuno;
    private int almuerzo;
    private int merienda;

    public RacionSimpleDTO(Integer id, Integer idqr, Integer idPersonal, int desayuno, int almuerzo, int merienda) {
        this.id = id;
        this.idqr = idqr;
        this.idPersonal = idPersonal;
        this.desayuno = desayuno;
        this.almuerzo = almuerzo;
        this.merienda = merienda;
    }

    public Integer getId() { return id; }
    public Integer getIdqr() { return idqr; }
    public Integer getIdPersonal() { return idPersonal; }
    public int getDesayuno() { return desayuno; }
    public int getAlmuerzo() { return almuerzo; }
    public int getMerienda() { return merienda; }

    public void setId(Integer id) { this.id = id; }
    public void setIdqr(Integer idqr) { this.idqr = idqr; }
    public void setIdPersonal(Integer idPersonal) { this.idPersonal = idPersonal; }
    public void setDesayuno(int desayuno) { this.desayuno = desayuno; }
    public void setAlmuerzo(int almuerzo) { this.almuerzo = almuerzo; }
    public void setMerienda(int merienda) { this.merienda = merienda; }
}

