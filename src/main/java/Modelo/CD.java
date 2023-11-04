package Modelo;

public class CD {
    int idEstado;
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
}
