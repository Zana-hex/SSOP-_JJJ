package Modelo;

public class Recurso {
    int id, procesoPropietario, ubicacion;
    String estado, tipo;

    public Recurso(int id, String estado, String tipo, int ubicacion) {
        this.id = id;
        this.estado = estado;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
    }

    public int getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(int ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProcesoPropietario() {
        return procesoPropietario;
    }

    public void setProcesoPropietario(int procesoPropietario) {
        this.procesoPropietario = procesoPropietario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
