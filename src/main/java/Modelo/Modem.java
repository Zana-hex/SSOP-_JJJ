package Modelo;

public class Modem {
    int idModem, procesoPropietario;
    String estado;

    public Modem(int id, String state){

        this.idModem = id;
        this.estado = state;

    }

    public int getIdModem() {
        return idModem;
    }

    public void setIdModem(int idModem) {
        this.idModem = idModem;
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
