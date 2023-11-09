package Modelo;

public class Escaner {
    int idEscaner, procesoPropietario;
    String estado;


    public Escaner (int id, String state){
        this.idEscaner = id;
        this.estado = state;
    }

    public int getIdEscaner() {
        return idEscaner;
    }

    public void setIdEscaner(int idEscaner) {
        this.idEscaner = idEscaner;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getProcesoPropietario() {
        return procesoPropietario;
    }

    public void setProcesoPropietario(int procesoPropietario) {
        this.procesoPropietario = procesoPropietario;
    }
}
