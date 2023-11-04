package Modelo;

public class Escaner {
    int idEscaner, procesoPropietario;
    String estado;


    public Escaner (int id, String state){
        this.idEscaner = id;
        this.estado = state;
    }

    public int getProcesoPropietario() {
        return procesoPropietario;
    }

    public void setProcesoPropietario(int procesoPropietario) {
        this.procesoPropietario = procesoPropietario;
    }
}
