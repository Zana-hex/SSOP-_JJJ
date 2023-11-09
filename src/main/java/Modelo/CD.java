package Modelo;

public class CD {
    int idEstado;
    int procesoPropietario;
    String estado;

    public CD(int id, String state) {
        this.idEstado = id;
        this.estado = state;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getProcesoPropietario() {
        return procesoPropietario;
    }

    public void setProcesoPropietario(int procesoPropietario) {
        this.procesoPropietario = procesoPropietario;
    }
}
